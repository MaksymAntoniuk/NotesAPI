package io.maksym.web.NoteTests;

import io.maksym.web.Records.NoteBody;
import io.maksym.web.Records.NoteCompletedBody;
import io.maksym.web.Records.NoteUpdateBody;
import io.maksym.web.base.BaseTest;
import io.maksym.web.dto.Note.Note;
import io.maksym.web.requests.actions.SimpleAction;
import io.maksym.web.util.DataGenerators;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.maksym.web.util.SchemaResponseValidator.assertResponseSchema;
import static org.junit.jupiter.api.Assertions.*;

@Epic("Note API")
@DisplayName("Verify that user is able to change [Note] fields")
@Severity(SeverityLevel.NORMAL)
public class UpdateNoteTest extends BaseTest {
    @DisplayName("Verify that user is able to change Completed status")
    @Description("""
            1. Create new Note
            2. Send request to Update [Status] of Note
            3. Assert response
            """)
    @Test
    public void updateNoteStatusTest(){

        String title = new DataGenerators().generateRandomTitle();
        String description = new DataGenerators().generateRandomDescription();
        String category = new DataGenerators().generateRandomCategoryNote();

        Response createNote = SimpleAction.createNote(token, new NoteBody(title,description, category));
        assertResponseSchema("create-note-response-schema.json", createNote);
        assertEquals(HttpStatus.SC_OK, createNote.getStatusCode(), "Incorrect status code");
        String noteId = createNote.as(Note.class).getData().getId();

        assertFalse(createNote.getBody().jsonPath().getBoolean("data.completed"), "Incorrect [Completed] status");

        Response updateCompletedStatus = updateCompletedStatus(token, noteId, new NoteCompletedBody(true));
        assertResponseSchema("create-note-response-schema.json", updateCompletedStatus);
        assertEquals(HttpStatus.SC_OK, updateCompletedStatus.getStatusCode(), "Incorrect status code");

        Note response = updateCompletedStatus.as(Note.class);
        assertAll("Update Note Completed status",
                () -> assertEquals(HttpStatus.SC_OK, response.getStatus(), "Invalid Status Code"),
                () -> assertTrue(response.isSuccess(), "Invalid Success Status"),
                () -> assertEquals(noteId, response.getData().getId(), "Invalid Note Id"),
                () -> assertEquals(title, response.getData().getTitle(), "Invalid Title"),
                () -> assertEquals(description, response.getData().getDescription(),"Invalid Description"),
                () -> assertEquals(category, response.getData().getCategory(), "Invalid Category"),
                () -> assertTrue(response.getData().isCompleted(), "Invalid [Completed] status"));
    }

    @DisplayName("Verify that user is able to update [Note] fields")
    @Description("""
            1. Create new Note
            2. Send request to Update all fields
            3. Assert response
            """)
    @Test
    public void updateNoteFieldsTest(){

        String title = new DataGenerators().generateRandomTitle();
        String description = new DataGenerators().generateRandomDescription();
        String category = new DataGenerators().generateRandomCategoryNote();

        Response createNote = SimpleAction.createNote(token, new NoteBody(title,description, category));
        assertResponseSchema("create-note-response-schema.json", createNote);
        assertEquals(HttpStatus.SC_OK, createNote.getStatusCode(), "Incorrect status code");
        String noteId = createNote.as(Note.class).getData().getId();

        String newTitle = new DataGenerators().generateRandomTitle();
        String newDescription = new DataGenerators().generateRandomDescription();
        String newCategory = new DataGenerators().generateRandomCategoryNote();

        Response updateNote = updateNote(token, noteId, new NoteUpdateBody(newTitle, newDescription, true, newCategory) );
        assertResponseSchema("create-note-response-schema.json", updateNote);
        assertEquals(HttpStatus.SC_OK, updateNote.getStatusCode(), "Incorrect status code");

        Note response = updateNote.as(Note.class);

        assertAll("Update Note Completed status",
                () -> assertEquals(HttpStatus.SC_OK, response.getStatus(), "Invalid Status Code"),
                () -> assertTrue(response.isSuccess(), "Invalid Success Status"),
                () -> assertEquals(noteId, response.getData().getId(), "Invalid Note Id"),
                () -> assertEquals(newTitle, response.getData().getTitle(), "Invalid Title"),
                () -> assertEquals(newDescription, response.getData().getDescription(),"Invalid Description"),
                () -> assertEquals(newCategory, response.getData().getCategory(), "Invalid Category"),
                () -> assertTrue(response.getData().isCompleted(), "Invalid [Completed] status"));

        deleteNoteById(token, noteId);
    }
}
