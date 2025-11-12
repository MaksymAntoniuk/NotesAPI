package io.maksym.web.NoteTest;

import io.maksym.web.Records.NoteBody;
import io.maksym.web.base.BaseTest;
import io.maksym.web.dto.HealthCheck.HealthCheckResponse;
import io.maksym.web.dto.Note.Note;
import io.maksym.web.requests.actions.SimpleAction;
import io.maksym.web.util.DataGenerators;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import static io.maksym.web.enums.ErrorMessage.NOTE_NOT_FOUND;
import static io.maksym.web.enums.ErrorMessage.SUCCESSFUL_DELETION_NOTE;
import static io.maksym.web.enums.StatusCode.SUCCESSFUL_STATUS;
import static io.maksym.web.util.Constants.EXPECTED_SUCCESS_TRUE;
import static io.maksym.web.util.Constants.REPEAT_COUNT;
import static io.maksym.web.util.SchemaResponseValidator.assertResponseSchema;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DeleteNoteTest extends BaseTest {

    @RepeatedTest(value = REPEAT_COUNT, name = "{displayName} : {currentRepetition}/{totalRepetitions}")
    @DisplayName("Verify that user is able to delete [Note]")
    public void deleteNoteTest() {
        String title = new DataGenerators().generateRandomTitle();
        String description = new DataGenerators().generateRandomDescription();
        String category = new DataGenerators().generateRandomCategoryNote();

        Response createNote = SimpleAction.createNote(token, new NoteBody(title,description, category));
        assertResponseSchema("create-note-response-schema.json", createNote);
        assertEquals(HttpStatus.SC_OK, createNote.getStatusCode(), "Incorrect status code");
        String noteId = createNote.as(Note.class).getData().getId();

        Response deleteNote = deleteNoteById(token, noteId);
        assertResponseSchema("delete-note-response-schema.json", deleteNote);
        assertEquals(HttpStatus.SC_OK, deleteNote.getStatusCode(), "Incorrect status code");

        HealthCheckResponse response = deleteNote.as(HealthCheckResponse.class);


        assertAll("Delete Note by Id",
                () -> assertEquals(EXPECTED_SUCCESS_TRUE, response.isSuccess(), "Invalid Success status"),
                () -> assertEquals(SUCCESSFUL_STATUS.getStatus(), response.getStatus(), "Invalid Status code "),
                () -> assertEquals(SUCCESSFUL_DELETION_NOTE.getMessage(), response.getMessage(), "Invalid Message" )

        );


        Response getNoteById = getNoteById(token, noteId);
        assertResponseSchema("delete-note-response-schema.json", getNoteById);
        assertEquals(HttpStatus.SC_NOT_FOUND, getNoteById.getStatusCode(), "Incorrect status code");
        assertEquals(NOTE_NOT_FOUND.getMessage(), getNoteById.getBody().jsonPath().getString("message"), "Invalid Message" );

    }
}
