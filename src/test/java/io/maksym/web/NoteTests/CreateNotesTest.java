package io.maksym.web.NoteTest;

import io.maksym.web.Records.NoteBody;
import io.maksym.web.base.BaseTest;
import io.maksym.web.dto.Note.Note;
import io.maksym.web.util.DataGenerators;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import static io.maksym.web.enums.ErrorMessage.SUCCESSFUL_CREATION_NOTE;
import static io.maksym.web.requests.actions.SimpleAction.createNote;
import static io.maksym.web.util.Constants.REPEAT_COUNT;
import static io.maksym.web.util.SchemaResponseValidator.assertResponseSchema;
import static org.junit.jupiter.api.Assertions.*;

public class CreateNotesTest extends BaseTest {

    @RepeatedTest(value = REPEAT_COUNT, name = "{displayName} : {currentRepetition}/{totalRepetitions}")
    @DisplayName("Verify that user is able to create new [Note]")
    public void createNoteTest(){
        String title = new DataGenerators().generateRandomTitle();
        String description = new DataGenerators().generateRandomDescription();
        String category = new DataGenerators().generateRandomCategoryNote();

        Response createNote = createNote(token, new NoteBody(title, description, category));
        assertResponseSchema("create-note-response-schema.json", createNote);
        assertEquals(HttpStatus.SC_OK, createNote.getStatusCode(), "Incorrect status code");

        Note response = createNote.as(Note.class);

        assertAll("Create valid Note",
                () -> assertEquals(HttpStatus.SC_OK, response.getStatus(), "Invalid Status Code"),
                () -> assertTrue(response.isSuccess(), "Invalid Success Status"),
                () -> assertEquals(SUCCESSFUL_CREATION_NOTE.getMessage(), response.getMessage(), "Invalid Message" ),
                () -> assertEquals(title, response.getData().getTitle(), "Invalid Title"),
                () -> assertEquals(description, response.getData().getDescription(),"Invalid Description"),
                () -> assertEquals(category, response.getData().getCategory(), "Invalid Category")
        );

        deleteNoteById(token, response.getData().getId());
    }
}
