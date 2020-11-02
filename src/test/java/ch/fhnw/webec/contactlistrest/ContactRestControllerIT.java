package ch.fhnw.webec.contactlistrest;

import ch.fhnw.webec.contactlistrest.model.Contact;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ContactRestControllerIT {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void shouldReturnAllContacts() {
        // when
        final ResponseEntity<List> result = restTemplate.getForEntity("/api/contacts", List.class);

        // then
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).hasSize(30);
    }

    @Test
    void shouldReturnSingleContactById() {
        // when
        final ResponseEntity<Contact> result = restTemplate.getForEntity("/api/contacts/1", Contact.class);

        // then
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody().getFirstName()).isEqualTo("Mabel");
        assertThat(result.getBody().getLastName()).isEqualTo("Guppy");
    }

    @Test
    void shouldReturnNotFound() {
        // when
        final ResponseEntity<Contact> result = restTemplate.getForEntity("/api/contacts/1000", Contact.class);

        // then
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void shouldReturnContactsFilteredByName() {
        // when
        final ResponseEntity<List> result = restTemplate.getForEntity("/api/contacts?name=ma", List.class);

        // then
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).hasSize(3);

    }
}
