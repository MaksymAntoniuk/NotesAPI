package io.maksym.web.NoteTests;

import io.maksym.web.Records.NoteBody;
import io.maksym.web.base.BaseTest;
import io.maksym.web.dto.Note.Note;
import io.maksym.web.dto.Note.NoteList;
import io.maksym.web.requests.actions.SimpleAction;
import io.maksym.web.util.DataGenerators;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Severity;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

import static io.maksym.web.enums.ErrorMessage.SUCCESSFUL_FETCH_ALL_NOTES;
import static io.maksym.web.util.Constants.REPEAT_COUNT;
import static io.maksym.web.util.SchemaResponseValidator.assertResponseSchema;
import static org.junit.jupiter.api.Assertions.*;

@Epic("Note API")
@DisplayName("Verify that user is able to get [Notes]")
@Severity(io.qameta.allure.SeverityLevel.CRITICAL)
public class GetNotesTest extends BaseTest {

    @RepeatedTest(value = REPEAT_COUNT, name = "{displayName} : {currentRepetition}/{totalRepetitions}")
    @Description("""
            1. Send request to Get all Notes
            2. Assert response
            """)
    @DisplayName("Verify that user is able to get all [Notes]")
    public void getNotesTest(){
        String title = new DataGenerators().generateRandomTitle();
        String description = new DataGenerators().generateRandomDescription();
        String category = new DataGenerators().generateRandomCategoryNote();

        Response createNote = SimpleAction.createNote(token, new NoteBody(title,description, category));
        assertResponseSchema("create-note-response-schema.json", createNote);
        assertEquals(HttpStatus.SC_OK, createNote.getStatusCode(), "Incorrect status code");
        String noteId = createNote.as(Note.class).getData().getId();

        Response getAllNotes = getAllNotes(token);
        assertResponseSchema("get-all-notes-response-schema.json", getAllNotes);

        NoteList notes = getAllNotes.as(NoteList.class);
        List<String> ids = new ArrayList<>();
        AtomicBoolean exist = new AtomicBoolean(false);

        notes.getData().forEach(note -> {
            if (Objects.equals(note.getId(), noteId)) {
                exist.set(true);
            }
            ids.add(note.getId());
        });

        assertAll("Get all Notes",
                () -> assertEquals(HttpStatus.SC_OK, notes.getStatus(), "Invalid Message"),
                () -> assertTrue(notes.isSuccess(), "Invalid Message"),
                () -> assertEquals(SUCCESSFUL_FETCH_ALL_NOTES.getMessage(), notes.getMessage(), "Invalid Message"),
                () -> assertTrue(exist.get(),  "Invalid Note Id" )
        );
        deleteNoteById(token, noteId);
    }

    @RepeatedTest(value = REPEAT_COUNT, name = "{displayName} : {currentRepetition}/{totalRepetitions}")
    @DisplayName("Verify that user is able to get [Note] by Id")
    @Description("""
            1. Create new Note
            2. Send request to get Note by ID
            3. Assert response
            """)
    public void getNoteByIdTest(){
        String title = new DataGenerators().generateRandomTitle();
        String description = new DataGenerators().generateRandomDescription();
        String category = new DataGenerators().generateRandomCategoryNote();

        Response createNote = SimpleAction.createNote(token, new NoteBody(title,description, category));
        assertResponseSchema("create-note-response-schema.json", createNote);
        assertEquals(HttpStatus.SC_OK, createNote.getStatusCode(), "Incorrect status code");
        String noteId = createNote.as(Note.class).getData().getId();

        Response getNoteById = getNoteById(token, noteId);
        assertResponseSchema("create-note-response-schema.json", getNoteById);
        assertEquals(HttpStatus.SC_OK, getNoteById.getStatusCode(), "Incorrect status code");

        Note note = getNoteById.as(Note.class);
        assertAll("Get Note by Id",
                () -> assertEquals(HttpStatus.SC_OK, note.getStatus(), "Invalid Status Code"),
                () -> assertTrue(note.isSuccess(), "Invalid Success Status"),
                () -> assertEquals(title, note.getData().getTitle(), "Invalid Title"),
                () -> assertEquals(description, note.getData().getDescription(),"Invalid Description"),
                () -> assertEquals(category, note.getData().getCategory(), "Invalid Category")
        );

        deleteNoteById(token, noteId);
    }
}
