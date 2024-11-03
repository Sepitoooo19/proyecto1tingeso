package com.example.PrestaBanco.services;

import com.example.PrestaBanco.entities.ClientEntity;
import com.example.PrestaBanco.repositories.ClientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

public class ClientServiceTest {

    @InjectMocks
    private ClientService clientService;

    @Mock
    private ClientRepository clientRepository;



    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // 1. Prueba para devolver una lista con clientes
    @Test
    public void whenFindAll_thenReturnClients() {
        // given
        List<ClientEntity> clients = Arrays.asList(
                new ClientEntity(null, "John Doe", "12345678-9", "john@example.com", "123456789", 30, 2000.0, 10000.0, "Software Engineer", 50000.0, 12, 2.5, "Personal Loan", false, 5, "Developer"),
                new ClientEntity(null, "Jane Doe", "98765432-1", "jane@example.com", "987654321", 28, 3000.0, 15000.0, "Designer", 60000.0, 24, 3.0, "Mortgage", true, 8, "Senior Designer")
        );
        given(clientRepository.findAll()).willReturn(clients);

        // when
        List<ClientEntity> foundClients = clientService.findAll();

        // then
        assertThat(foundClients).isNotEmpty();
        assertThat(foundClients.size()).isEqualTo(2);
    }

    // 2. Prueba cuando no hay clientes
    @Test
    public void whenFindAll_thenReturnEmptyList() {
        // given
        given(clientRepository.findAll()).willReturn(Collections.emptyList());

        // when
        List<ClientEntity> foundClients = clientService.findAll();

        // then
        assertThat(foundClients).isEmpty();
    }

    // 3. Prueba para verificar que el método del repositorio se llama una vez
    @Test
    public void whenFindAll_thenRepositoryMethodCalledOnce() {
        // given
        List<ClientEntity> clients = Arrays.asList(
                new ClientEntity(null, "John Doe", "12345678-9", "john@example.com", "123456789", 30, 2000.0, 10000.0, "Software Engineer", 50000.0, 12, 2.5, "Personal Loan", false, 5, "Developer")
        );
        given(clientRepository.findAll()).willReturn(clients);

        // when
        clientService.findAll();

        // then
        verify(clientRepository, times(1)).findAll();
    }

    // 4. Prueba para asegurar que se devuelve una lista de un solo cliente
    @Test
    public void whenFindAll_thenReturnSingleClient() {
        // given
        List<ClientEntity> clients = Arrays.asList(
                new ClientEntity(null, "John Doe", "12345678-9", "john@example.com", "123456789", 30, 2000.0, 10000.0, "Software Engineer", 50000.0, 12, 2.5, "Personal Loan", false, 5, "Developer")
        );
        given(clientRepository.findAll()).willReturn(clients);

        // when
        List<ClientEntity> foundClients = clientService.findAll();

        // then
        assertThat(foundClients.size()).isEqualTo(1);
        assertThat(foundClients.get(0).getName()).isEqualTo("John Doe");
    }

    // 5. Prueba con clientes con diferentes edades
    @Test
    public void whenFindAll_thenClientsHaveDifferentAges() {
        // given
        List<ClientEntity> clients = Arrays.asList(
                new ClientEntity(null, "John Doe", "12345678-9", "john@example.com", "123456789", 30, 2000.0, 10000.0, "Software Engineer", 50000.0, 12, 2.5, "Personal Loan", false, 5, "Developer"),
                new ClientEntity(null, "Jane Smith", "98765432-1", "jane@example.com", "987654321", 25, 3000.0, 15000.0, "Designer", 60000.0, 24, 3.0, "Mortgage", true, 8, "Senior Designer")
        );
        given(clientRepository.findAll()).willReturn(clients);

        // when
        List<ClientEntity> foundClients = clientService.findAll();

        // then
        assertThat(foundClients.get(0).getAge()).isNotEqualTo(foundClients.get(1).getAge());
    }

    // 6. Prueba para verificar que los clientes tienen diferentes correos electrónicos
    @Test
    public void whenFindAll_thenClientsHaveDifferentEmails() {
        // given
        List<ClientEntity> clients = Arrays.asList(
                new ClientEntity(null, "John Doe", "12345678-9", "john@example.com", "123456789", 30, 2000.0, 10000.0, "Software Engineer", 50000.0, 12, 2.5, "Personal Loan", false, 5, "Developer"),
                new ClientEntity(null, "Jane Smith", "98765432-1", "jane@example.com", "987654321", 25, 3000.0, 15000.0, "Designer", 60000.0, 24, 3.0, "Mortgage", true, 8, "Senior Designer")
        );
        given(clientRepository.findAll()).willReturn(clients);

        // when
        List<ClientEntity> foundClients = clientService.findAll();

        // then
        assertThat(foundClients.get(0).getEmail()).isNotEqualTo(foundClients.get(1).getEmail());
    }

    // 7. Prueba para asegurar que se devuelven clientes con diferentes montos de ingresos
    @Test
    public void whenFindAll_thenClientsHaveDifferentIncome() {
        // given
        List<ClientEntity> clients = Arrays.asList(
                new ClientEntity(null, "John Doe", "12345678-9", "john@example.com", "123456789", 30, 2000.0, 10000.0, "Software Engineer", 50000.0, 12, 2.5, "Personal Loan", false, 5, "Developer"),
                new ClientEntity(null, "Jane Smith", "98765432-1", "jane@example.com", "987654321", 25, 3000.0, 15000.0, "Designer", 60000.0, 24, 3.0, "Mortgage", true, 8, "Senior Designer")
        );
        given(clientRepository.findAll()).willReturn(clients);

        // when
        List<ClientEntity> foundClients = clientService.findAll();

        // then
        assertThat(foundClients.get(0).getMonthly_salary()).isNotEqualTo(foundClients.get(1).getMonthly_salary());
    }
    // 8. Prueba para asegurar que se devuelve una lista de clientes con diferentes ocupaciones
    @Test
    public void whenFindAll_thenClientsHaveDifferentOccupations() {
        // given
        List<ClientEntity> clients = Arrays.asList(
                new ClientEntity(null, "John Doe", "12345678-9", "john@example.com", "123456789", 30, 2000.0, 10000.0, "Software Engineer", 50000.0, 12, 2.5, "Personal Loan", false, 5, "Developer"),
                new ClientEntity(null, "Jane Smith", "98765432-1", "jane@example.com", "987654321", 25, 3000.0, 15000.0, "Designer", 60000.0, 24, 3.0, "Mortgage", true, 8, "Senior Designer")
        );
        given(clientRepository.findAll()).willReturn(clients);

        // when
        List<ClientEntity> foundClients = clientService.findAll();

        // then
        assertThat(foundClients.get(0).getJob_type()).isNotEqualTo(foundClients.get(1).getJob_type());
    }

    // 9. Prueba cuando el nombre del cliente contiene un número
    @Test
    public void whenFindAll_thenClientNameContainsNumber() {
        // given
        List<ClientEntity> clients = Arrays.asList(
                new ClientEntity(null, "John Doe2", "12345678-9", "john2@example.com", "123456789", 30, 2000.0, 10000.0, "Software Engineer", 50000.0, 12, 2.5, "Personal Loan", false, 5, "Developer")
        );
        given(clientRepository.findAll()).willReturn(clients);

        // when
        List<ClientEntity> foundClients = clientService.findAll();

        // then
        assertThat(foundClients.get(0).getName()).contains("2");
    }

    // 1. Prueba cuando el cliente existe
    @Test
    public void whenFindByRut_thenReturnClient() {
        // given
        String rut = "12345678-9";
        ClientEntity client = new ClientEntity(null, "John Doe", rut, "john@example.com", "123456789", 30, 2000.0, 10000.0, "Software Engineer", 50000.0, 12, 2.5, "Personal Loan", false, 5, "Developer");
        given(clientRepository.findByRut(rut)).willReturn(client);

        // when
        ClientEntity foundClient = clientService.findByRut(rut);

        // then
        assertThat(foundClient).isNotNull();
        assertThat(foundClient.getRut()).isEqualTo(rut);
    }

    // 2. Prueba cuando el cliente no existe
    @Test
    public void whenFindByRut_thenReturnNull() {
        // given
        String rut = "12345678-9";
        given(clientRepository.findByRut(rut)).willReturn(null);

        // when
        ClientEntity foundClient = clientService.findByRut(rut);

        // then
        assertThat(foundClient).isNull();
    }

    // 3. Prueba cuando el RUT es nulo
    @Test
    public void whenFindByRutWithNullRut_thenReturnNull() {
        // given
        given(clientRepository.findByRut(null)).willReturn(null);

        // when
        ClientEntity foundClient = clientService.findByRut(null);

        // then
        assertThat(foundClient).isNull();
    }

    // 4. Prueba cuando el RUT tiene un formato incorrecto
    @Test
    public void whenFindByRutWithInvalidFormat_thenReturnNull() {
        // given
        String invalidRut = "invalid-rut";
        given(clientRepository.findByRut(invalidRut)).willReturn(null);

        // when
        ClientEntity foundClient = clientService.findByRut(invalidRut);

        // then
        assertThat(foundClient).isNull();
    }

    // 5. Prueba que se llame al método del repositorio una vez
    @Test
    public void whenFindByRut_thenRepositoryMethodCalledOnce() {
        // given
        String rut = "12345678-9";
        ClientEntity client = new ClientEntity(null, "John Doe", rut, "john@example.com", "123456789", 30, 2000.0, 10000.0, "Software Engineer", 50000.0, 12, 2.5, "Personal Loan", false, 5, "Developer");
        given(clientRepository.findByRut(rut)).willReturn(client);

        // when
        clientService.findByRut(rut);

        // then
        verify(clientRepository, times(1)).findByRut(rut);
    }

    // 6. Prueba cuando se busca un cliente por un RUT que contiene espacios
    @Test
    public void whenFindByRutWithSpaces_thenReturnClient() {
        // given
        String rutWithSpaces = " 12345678-9 ";
        String rutTrimmed = "12345678-9";
        ClientEntity client = new ClientEntity(null, "John Doe", rutTrimmed, "john@example.com", "123456789", 30, 2000.0, 10000.0, "Software Engineer", 50000.0, 12, 2.5, "Personal Loan", false, 5, "Developer");
        given(clientRepository.findByRut(rutTrimmed)).willReturn(client);

        // when
        ClientEntity foundClient = clientService.findByRut(rutWithSpaces.trim());

        // then
        assertThat(foundClient).isNotNull();
        assertThat(foundClient.getRut()).isEqualTo(rutTrimmed);
    }

    // 7. Prueba cuando se busca un cliente por un RUT en minúsculas
    @Test
    public void whenFindByRutLowerCase_thenReturnClient() {
        // given
        String rutLowerCase = "12345678-9";
        ClientEntity client = new ClientEntity(null, "John Doe", rutLowerCase, "john@example.com", "123456789", 30, 2000.0, 10000.0, "Software Engineer", 50000.0, 12, 2.5, "Personal Loan", false, 5, "Developer");
        given(clientRepository.findByRut(rutLowerCase)).willReturn(client);

        // when
        ClientEntity foundClient = clientService.findByRut(rutLowerCase);

        // then
        assertThat(foundClient).isNotNull();
        assertThat(foundClient.getRut()).isEqualTo(rutLowerCase);
    }
    // 1. Prueba cuando el cliente existe por email
    @Test
    public void whenFindByEmail_thenReturnClient() {
        // given
        String email = "john@example.com";
        ClientEntity client = new ClientEntity(null, "John Doe", "12345678-9", email, "123456789", 30, 2000.0, 10000.0, "Software Engineer", 50000.0, 12, 2.5, "Personal Loan", false, 5, "Developer");
        given(clientRepository.findByEmail(email)).willReturn(client);

        // when
        ClientEntity foundClient = clientService.findByEmail(email);

        // then
        assertThat(foundClient).isNotNull();
        assertThat(foundClient.getEmail()).isEqualTo(email);
    }

    // 2. Prueba cuando el cliente no existe
    @Test
    public void whenFindByEmail_thenReturnNull() {
        // given
        String email = "nonexistent@example.com";
        given(clientRepository.findByEmail(email)).willReturn(null);

        // when
        ClientEntity foundClient = clientService.findByEmail(email);

        // then
        assertThat(foundClient).isNull();
    }

    // 3. Prueba cuando el email es nulo
    @Test
    public void whenFindByEmailWithNullEmail_thenReturnNull() {
        // given
        given(clientRepository.findByEmail(null)).willReturn(null);

        // when
        ClientEntity foundClient = clientService.findByEmail(null);

        // then
        assertThat(foundClient).isNull();
    }

    // 4. Prueba cuando el email tiene formato incorrecto
    @Test
    public void whenFindByEmailWithInvalidFormat_thenReturnNull() {
        // given
        String invalidEmail = "invalid-email";
        given(clientRepository.findByEmail(invalidEmail)).willReturn(null);

        // when
        ClientEntity foundClient = clientService.findByEmail(invalidEmail);

        // then
        assertThat(foundClient).isNull();
    }

    // 5. Prueba que el método del repositorio sea llamado una vez
    @Test
    public void whenFindByEmail_thenRepositoryMethodCalledOnce() {
        // given
        String email = "john@example.com";
        ClientEntity client = new ClientEntity(null, "John Doe", "12345678-9", email, "123456789", 30, 2000.0, 10000.0, "Software Engineer", 50000.0, 12, 2.5, "Personal Loan", false, 5, "Developer");
        given(clientRepository.findByEmail(email)).willReturn(client);

        // when
        clientService.findByEmail(email);

        // then
        verify(clientRepository, times(1)).findByEmail(email);
    }

    // 6. Prueba cuando el email contiene mayúsculas
    @Test
    public void whenFindByEmailWithUpperCase_thenReturnClient() {
        // given
        String emailWithUpperCase = "JOHN@EXAMPLE.COM";
        String emailLowerCase = "john@example.com";
        ClientEntity client = new ClientEntity(null, "John Doe", "12345678-9", emailLowerCase, "123456789", 30, 2000.0, 10000.0, "Software Engineer", 50000.0, 12, 2.5, "Personal Loan", false, 5, "Developer");
        given(clientRepository.findByEmail(emailLowerCase)).willReturn(client);

        // when
        ClientEntity foundClient = clientService.findByEmail(emailWithUpperCase.toLowerCase());

        // then
        assertThat(foundClient).isNotNull();
        assertThat(foundClient.getEmail()).isEqualTo(emailLowerCase);
    }

    // 7. Prueba cuando el email contiene espacios en blanco
    @Test
    public void whenFindByEmailWithSpaces_thenReturnClient() {
        // given
        String emailWithSpaces = " john@example.com ";
        String emailTrimmed = "john@example.com";
        ClientEntity client = new ClientEntity(null, "John Doe", "12345678-9", emailTrimmed, "123456789", 30, 2000.0, 10000.0, "Software Engineer", 50000.0, 12, 2.5, "Personal Loan", false, 5, "Developer");
        given(clientRepository.findByEmail(emailTrimmed)).willReturn(client);

        // when
        ClientEntity foundClient = clientService.findByEmail(emailWithSpaces.trim());

        // then
        assertThat(foundClient).isNotNull();
        assertThat(foundClient.getEmail()).isEqualTo(emailTrimmed);
    }

    // 1. Prueba cuando el cliente existe por ID
    @Test
    public void whenFindByClientId_thenReturnClient() {
        // given
        Long clientId = 1L;
        ClientEntity client = new ClientEntity(clientId, "John Doe", "12345678-9", "john@example.com", "123456789", 30, 2000.0, 10000.0, "Software Engineer", 50000.0, 12, 2.5, "Personal Loan", false, 5, "Developer");
        given(clientRepository.findByClientId(clientId)).willReturn(client);

        // when
        ClientEntity foundClient = clientService.findByClientId(clientId);

        // then
        assertThat(foundClient).isNotNull();
        assertThat(foundClient.getClient_id()).isEqualTo(clientId);
    }

    // 2. Prueba cuando el cliente no existe
    @Test
    public void whenFindByClientId_thenReturnNull() {
        // given
        Long clientId = 1L;
        given(clientRepository.findByClientId(clientId)).willReturn(null);

        // when
        ClientEntity foundClient = clientService.findByClientId(clientId);

        // then
        assertThat(foundClient).isNull();
    }

    // 3. Prueba cuando el ID es nulo
    @Test
    public void whenFindByClientIdWithNullId_thenReturnNull() {
        // given
        given(clientRepository.findByClientId(null)).willReturn(null);

        // when
        ClientEntity foundClient = clientService.findByClientId(null);

        // then
        assertThat(foundClient).isNull();
    }

    // 4. Prueba cuando el ID es negativo
    @Test
    public void whenFindByClientIdWithNegativeId_thenReturnNull() {
        // given
        Long negativeId = -1L;
        given(clientRepository.findByClientId(negativeId)).willReturn(null);

        // when
        ClientEntity foundClient = clientService.findByClientId(negativeId);

        // then
        assertThat(foundClient).isNull();
    }

    // 5. Prueba que el método del repositorio sea llamado una vez
    @Test
    public void whenFindByClientId_thenRepositoryMethodCalledOnce() {
        // given
        Long clientId = 1L;
        ClientEntity client = new ClientEntity(clientId, "John Doe", "12345678-9", "john@example.com", "123456789", 30, 2000.0, 10000.0, "Software Engineer", 50000.0, 12, 2.5, "Personal Loan", false, 5, "Developer");
        given(clientRepository.findByClientId(clientId)).willReturn(client);

        // when
        clientService.findByClientId(clientId);

        // then
        verify(clientRepository, times(1)).findByClientId(clientId);
    }

    // 6. Prueba cuando el ID es grande
    @Test
    public void whenFindByClientIdWithLargeId_thenReturnClient() {
        // given
        Long largeClientId = 100000000L;
        ClientEntity client = new ClientEntity(largeClientId, "Jane Smith", "98765432-1", "jane@example.com", "987654321", 28, 3000.0, 15000.0, "Designer", 60000.0, 24, 3.0, "Mortgage", true, 8, "Senior Designer");
        given(clientRepository.findByClientId(largeClientId)).willReturn(client);

        // when
        ClientEntity foundClient = clientService.findByClientId(largeClientId);

        // then
        assertThat(foundClient).isNotNull();
        assertThat(foundClient.getClient_id()).isEqualTo(largeClientId);
    }

    // 7. Prueba cuando se busca un cliente con ID 0
    @Test
    public void whenFindByClientIdWithZeroId_thenReturnNull() {
        // given
        Long zeroId = 0L;
        given(clientRepository.findByClientId(zeroId)).willReturn(null);

        // when
        ClientEntity foundClient = clientService.findByClientId(zeroId);

        // then
        assertThat(foundClient).isNull();
    }

    // 1. Prueba cuando el cliente existe por nombre
    @Test
    public void whenFindByName_thenReturnClient() {
        // given
        String name = "John Doe";
        String normalized = name.toLowerCase();
        ClientEntity client = new ClientEntity(null, name, "12345678-9", "john@example.com", "123456789", 30, 2000.0, 10000.0, "Software Engineer", 50000.0, 12, 2.5, "Personal Loan", false, 5, "Developer");
        given(clientRepository.findByName(normalized)).willReturn(client);

        // when
        ClientEntity foundClient = clientService.findByName(name);

        // then
        assertThat(foundClient).isNotNull();
        assertThat(foundClient.getName()).isEqualTo(name);
    }

    // 2. Prueba cuando el cliente no existe
    @Test
    public void whenFindByName_thenReturnNull() {
        // given
        String name = "Nonexistent Name";
        given(clientRepository.findByName(name)).willReturn(null);

        // when
        ClientEntity foundClient = clientService.findByName(name);

        // then
        assertThat(foundClient).isNull();
    }

    // 3. Prueba cuando el nombre es nulo
    @Test
    public void whenFindByNameWithNullName_thenReturnNull() {
        // given
        given(clientRepository.findByName(null)).willReturn(null);

        // when
        ClientEntity foundClient = clientService.findByName(null);

        // then
        assertThat(foundClient).isNull();
    }

    // 4. Prueba cuando el nombre está vacío
    @Test
    public void whenFindByNameWithEmptyName_thenReturnNull() {
        // given
        String emptyName = "";
        given(clientRepository.findByName(emptyName)).willReturn(null);

        // when
        ClientEntity foundClient = clientService.findByName(emptyName);

        // then
        assertThat(foundClient).isNull();
    }

    // 5. Prueba que el método del repositorio sea llamado una vez
    @Test
    public void whenFindByName_thenRepositoryMethodCalledOnce() {
        // given
        String name = "John Doe";
        String normalized = name.toLowerCase(); // Se debe comparar con el valor esperado en el repositorio
        ClientEntity client = new ClientEntity(null, name, "12345678-9", "john@example.com", "123456789", 30, 2000.0, 10000.0, "Software Engineer", 50000.0, 12, 2.5, "Personal Loan", false, 5, "Developer");
        given(clientRepository.findByName(normalized)).willReturn(client);

        // when
        clientService.findByName(name);

        // then
        verify(clientRepository, times(1)).findByName(normalized);
    }

    // 6. Prueba cuando el nombre contiene espacios en blanco
    @Test
    public void whenFindByNameWithSpaces_thenReturnClient() {
        // given
        String nameWithSpaces = " John Doe ";
        String trimmedName = "John Doe";
        ClientEntity client = new ClientEntity(null, trimmedName, "12345678-9", "john@example.com", "123456789", 30, 2000.0, 10000.0, "Software Engineer", 50000.0, 12, 2.5, "Personal Loan", false, 5, "Developer");
        given(clientRepository.findByName(trimmedName.toLowerCase())).willReturn(client);

        // when
        ClientEntity foundClient = clientService.findByName(nameWithSpaces);

        // then
        assertThat(foundClient).isNotNull();
        assertThat(foundClient.getName()).isEqualTo(trimmedName);
    }

    // 7. Prueba cuando el nombre contiene mayúsculas
    @Test
    public void whenFindByNameWithUpperCase_thenReturnClient() {
        // given
        String nameUpperCase = "JOHN DOE";
        String nameLowerCase = "john doe"; // Cambia a minúsculas si el servicio lo convierte
        ClientEntity client = new ClientEntity(null, nameLowerCase, "12345678-9", "john@example.com", "123456789", 30, 2000.0, 10000.0, "Software Engineer", 50000.0, 12, 2.5, "Personal Loan", false, 5, "Developer");
        given(clientRepository.findByName(nameLowerCase)).willReturn(client);

        // when
        ClientEntity foundClient = clientService.findByName(nameUpperCase);

        // then
        assertThat(foundClient).isNotNull();
        assertThat(foundClient.getName()).isEqualTo(nameLowerCase);
    }

    //1. Prueba cuando el teléfono es válido
    @Test
    public void whenFindByPhone_thenReturnClient() {
        // given
        String phone = "123456789";
        ClientEntity client = new ClientEntity(null, "John Doe", "12345678-9", "john@example.com", phone, 30, 2000.0, 10000.0, "Software Engineer", 50000.0, 12, 2.5, "Personal Loan", false, 5, "Developer");
        given(clientRepository.findByPhone(phone)).willReturn(client);

        // when
        ClientEntity foundClient = clientService.findByPhone(phone);

        // then
        assertThat(foundClient).isNotNull();
        assertThat(foundClient.getPhone()).isEqualTo(phone);
    }

    //2. Prueba cuando el telefono es nulo
    @Test
    public void whenFindByPhoneWithNullPhone_thenReturnNull() {
        // given
        given(clientRepository.findByPhone(null)).willReturn(null);

        // when
        ClientEntity foundClient = clientService.findByPhone(null);

        // then
        assertThat(foundClient).isNull();
    }

    //3. Prueba cuando el teléfono es vacío
    @Test
    public void whenFindByPhoneWithEmptyPhone_thenReturnNull() {
        // given
        String emptyPhone = "";
        given(clientRepository.findByPhone(emptyPhone)).willReturn(null);

        // when
        ClientEntity foundClient = clientService.findByPhone(emptyPhone);

        // then
        assertThat(foundClient).isNull();
    }

    //4. Prueba cuando el telefono tiene espacios en blanco
    @Test
    public void whenFindByPhoneWithSpaces_thenReturnClient() {
        // given
        String phoneWithSpaces = " 123456789 ";
        String trimmedPhone = "123456789";
        ClientEntity client = new ClientEntity(null, "John Doe", "12345678-9", "john@example.com", trimmedPhone, 30, 2000.0, 10000.0, "Software Engineer", 50000.0, 12, 2.5, "Personal Loan", false, 5, "Developer");
        given(clientRepository.findByPhone(trimmedPhone)).willReturn(client);

        // when
        ClientEntity foundClient = clientService.findByPhone(phoneWithSpaces.trim());

        // then
        assertThat(foundClient).isNotNull();
        assertThat(foundClient.getPhone()).isEqualTo(trimmedPhone);
    }

    // 5. Prueba que el numero de telefono contiene caracteres no numericos
    @Test
    public void whenFindByPhoneWithNonNumericCharacters_thenReturnNull() {
        // given
        String phoneWithChars = "123-456-789";
        given(clientRepository.findByPhone(phoneWithChars)).willReturn(null);

        // when
        ClientEntity foundClient = clientService.findByPhone(phoneWithChars);

        // then
        assertThat(foundClient).isNull();
    }

    //6. Prueba que no existe un cliente con el telefono dado
    @Test
    public void whenFindByPhone_thenReturnNullIfNotFound() {
        // given
        String phone = "987654321";
        given(clientRepository.findByPhone(phone)).willReturn(null);

        // when
        ClientEntity foundClient = clientService.findByPhone(phone);

        // then
        assertThat(foundClient).isNull();
    }

    //7. Prueba que el método del repositorio sea llamado una vez
    @Test
    public void whenFindByPhone_thenRepositoryMethodCalledOnce() {
        // given
        String phone = "123456789";
        ClientEntity client = new ClientEntity(null, "John Doe", "12345678-9", "john@example.com", phone, 30, 2000.0, 10000.0, "Software Engineer", 50000.0, 12, 2.5, "Personal Loan", false, 5, "Developer");
        given(clientRepository.findByPhone(phone)).willReturn(client);

        // when
        clientService.findByPhone(phone);

        // then
        verify(clientRepository, times(1)).findByPhone(phone);
    }

    // 1. Prueba cuando el tipo de trabajo es válido
    @Test
    public void whenFindByJobType_thenReturnClient() {
        // given
        String jobType = "Software Engineer";
        ClientEntity client = new ClientEntity(null, "John Doe", "12345678-9", "john@example.com", "123456789", 30, 2000.0, 10000.0, jobType, 50000.0, 12, 2.5, "Personal Loan", false, 5, "Developer");
        given(clientRepository.findByJobType(jobType)).willReturn(client);

        // when
        ClientEntity foundClient = clientService.findByJobType(jobType);

        // then
        assertThat(foundClient).isNotNull();
        assertThat(foundClient.getJob_type()).isEqualTo(jobType);
    }

    // 2. Prueba cuando el tipo de trabajo es null
    @Test
    public void whenFindByJobTypeWithNullJobType_thenReturnNull() {
        // given
        given(clientRepository.findByJobType(null)).willReturn(null);

        // when
        ClientEntity foundClient = clientService.findByJobType(null);

        // then
        assertThat(foundClient).isNull();
    }

    // 3. Prueba cuando el tipo de trabajo es una cadena vacía
    @Test
    public void whenFindByJobTypeWithEmptyJobType_thenReturnNull() {
        // given
        String emptyJobType = "";
        given(clientRepository.findByJobType(emptyJobType)).willReturn(null);

        // when
        ClientEntity foundClient = clientService.findByJobType(emptyJobType);

        // then
        assertThat(foundClient).isNull();
    }

    // 4. Prueba cuando el tipo de trabajo tiene espacios adicionales
    @Test
    public void whenFindByJobTypeWithSpaces_thenReturnClient() {
        // given
        String jobTypeWithSpaces = "  Software Engineer  ";
        String trimmedJobType = "Software Engineer";
        ClientEntity client = new ClientEntity(null, "John Doe", "12345678-9", "john@example.com", "123456789", 30, 2000.0, 10000.0, trimmedJobType, 50000.0, 12, 2.5, "Personal Loan", false, 5, "Developer");
        given(clientRepository.findByJobType(trimmedJobType)).willReturn(client);

        // when
        ClientEntity foundClient = clientService.findByJobType(jobTypeWithSpaces);

        // then
        assertThat(foundClient).isNotNull();
        assertThat(foundClient.getJob_type()).isEqualTo(trimmedJobType);
    }

    // 5. Prueba cuando el cliente no existe para el tipo de trabajo dado
    @Test
    public void whenFindByJobType_thenReturnNullIfNotFound() {
        // given
        String jobType = "Designer";
        given(clientRepository.findByJobType(jobType)).willReturn(null);

        // when
        ClientEntity foundClient = clientService.findByJobType(jobType);

        // then
        assertThat(foundClient).isNull();
    }

    // 6. Verificar que el repositorio es llamado una vez
    @Test
    public void whenFindByJobType_thenRepositoryMethodCalledOnce() {
        // given
        String jobType = "Software Engineer";
        ClientEntity client = new ClientEntity(null, "John Doe", "12345678-9", "john@example.com", "123456789", 30, 2000.0, 10000.0, jobType, 50000.0, 12, 2.5, "Personal Loan", false, 5, "Developer");
        given(clientRepository.findByJobType(jobType)).willReturn(client);

        // when
        clientService.findByJobType(jobType);

        // then
        verify(clientRepository, times(1)).findByJobType(jobType);
    }

    // 7. Prueba cuando el tipo de trabajo contiene caracteres no válidos
    @Test
    public void whenFindByJobTypeWithInvalidCharacters_thenReturnNull() {
        // given
        String jobTypeWithInvalidChars = "Software@Engineer";
        given(clientRepository.findByJobType(jobTypeWithInvalidChars)).willReturn(null);

        // when
        ClientEntity foundClient = clientService.findByJobType(jobTypeWithInvalidChars);

        // then
        assertThat(foundClient).isNull();
    }

    // 1. Prueba cuando el tipo de préstamo es válido
    @Test
    public void whenFindByTypeLoan_thenReturnClient() {
        // given
        String loanType = "Personal Loan";
        ClientEntity client = new ClientEntity(null, "John Doe", "12345678-9", "john@example.com", "123456789", 30, 2000.0, 10000.0, "Software Engineer", 50000.0, 12, 2.5, loanType, false, 5, "Developer");
        given(clientRepository.findByTypeLoan(loanType)).willReturn(client);

        // when
        ClientEntity foundClient = clientService.findByTypeLoan(loanType);

        // then
        assertThat(foundClient).isNotNull();
        assertThat(foundClient.getType_loan()).isEqualTo(loanType);
    }

    // 2. Prueba cuando el tipo de préstamo es null
    @Test
    public void whenFindByTypeLoanWithNullLoanType_thenReturnNull() {
        // given
        given(clientRepository.findByTypeLoan(null)).willReturn(null);

        // when
        ClientEntity foundClient = clientService.findByTypeLoan(null);

        // then
        assertThat(foundClient).isNull();
    }

    // 3. Prueba cuando el tipo de préstamo es una cadena vacía
    @Test
    public void whenFindByTypeLoanWithEmptyLoanType_thenReturnNull() {
        // given
        String emptyLoanType = "";
        given(clientRepository.findByTypeLoan(emptyLoanType)).willReturn(null);

        // when
        ClientEntity foundClient = clientService.findByTypeLoan(emptyLoanType);

        // then
        assertThat(foundClient).isNull();
    }

    // 4. Prueba cuando el tipo de préstamo tiene espacios adicionales
    @Test
    public void whenFindByTypeLoanWithSpaces_thenReturnClient() {
        // given
        String loanTypeWithSpaces = "  Personal Loan  ";
        String trimmedLoanType = "Personal Loan";
        ClientEntity client = new ClientEntity(null, "John Doe", "12345678-9", "john@example.com", "123456789", 30, 2000.0, 10000.0, "Software Engineer", 50000.0, 12, 2.5, trimmedLoanType, false, 5, "Developer");
        given(clientRepository.findByTypeLoan(trimmedLoanType)).willReturn(client);

        // when
        ClientEntity foundClient = clientService.findByTypeLoan(loanTypeWithSpaces);

        // then
        assertThat(foundClient).isNotNull();
        assertThat(foundClient.getType_loan()).isEqualTo(trimmedLoanType);
    }

    // 5. Prueba cuando el cliente no existe para el tipo de préstamo dado
    @Test
    public void whenFindByTypeLoan_thenReturnNullIfNotFound() {
        // given
        String loanType = "Mortgage";
        given(clientRepository.findByTypeLoan(loanType)).willReturn(null);

        // when
        ClientEntity foundClient = clientService.findByTypeLoan(loanType);

        // then
        assertThat(foundClient).isNull();
    }

    // 6. Verificar que el repositorio es llamado una vez
    @Test
    public void whenFindByTypeLoan_thenRepositoryMethodCalledOnce() {
        // given
        String loanType = "Personal Loan";
        ClientEntity client = new ClientEntity(null, "John Doe", "12345678-9", "john@example.com", "123456789", 30, 2000.0, 10000.0, "Software Engineer", 50000.0, 12, 2.5, loanType, false, 5, "Developer");
        given(clientRepository.findByTypeLoan(loanType)).willReturn(client);

        // when
        clientService.findByTypeLoan(loanType);

        // then
        verify(clientRepository, times(1)).findByTypeLoan(loanType);
    }

    // 7. Prueba cuando el tipo de préstamo contiene caracteres no válidos
    @Test
    public void whenFindByTypeLoanWithInvalidCharacters_thenReturnNull() {
        // given
        String loanTypeWithInvalidChars = "Personal@Loan";
        given(clientRepository.findByTypeLoan(loanTypeWithInvalidChars)).willReturn(null);

        // when
        ClientEntity foundClient = clientService.findByTypeLoan(loanTypeWithInvalidChars);

        // then
        assertThat(foundClient).isNull();
    }

    // 1. Prueba cuando la actividad independiente es verdadera
    @Test
    public void whenFindByIndependentActivityTrue_thenReturnClient() {
        // given
        ClientEntity client = new ClientEntity(null, "John Doe", "12345678-9", "john@example.com", "123456789", 30, 2000.0, 10000.0, "Software Engineer", 50000.0, 12, 2.5, "Personal Loan", false, 5, "Developer");
        given(clientRepository.findByIndependentActivity(true)).willReturn(client);

        // when
        ClientEntity foundClient = clientService.findByIndependentActivity(true);

        // then
        assertThat(foundClient).isNotNull();
        assertThat(foundClient.getName()).isEqualTo("John Doe");
    }

    // 2. Prueba cuando la actividad independiente es falsa
    @Test
    public void whenFindByIndependentActivityFalse_thenReturnClient() {
        // given
        ClientEntity client = new ClientEntity(null, "Jane Doe", "98765432-1", "jane@example.com", "987654321", 28, 3000.0, 15000.0, "Designer", 60000.0, 24, 3.0, "Mortgage", true, 8, "Senior Designer");
        given(clientRepository.findByIndependentActivity(false)).willReturn(client);

        // when
        ClientEntity foundClient = clientService.findByIndependentActivity(false);

        // then
        assertThat(foundClient).isNotNull();
        assertThat(foundClient.getName()).isEqualTo("Jane Doe");
    }

    // 3. Prueba cuando no hay cliente con actividad independiente verdadera
    @Test
    public void whenFindByIndependentActivityTrue_thenReturnNullIfNotFound() {
        // given
        given(clientRepository.findByIndependentActivity(true)).willReturn(null);

        // when
        ClientEntity foundClient = clientService.findByIndependentActivity(true);

        // then
        assertThat(foundClient).isNull();
    }

    // 4. Prueba cuando no hay cliente con actividad independiente falsa
    @Test
    public void whenFindByIndependentActivityFalse_thenReturnNullIfNotFound() {
        // given
        given(clientRepository.findByIndependentActivity(false)).willReturn(null);

        // when
        ClientEntity foundClient = clientService.findByIndependentActivity(false);

        // then
        assertThat(foundClient).isNull();
    }

    // 5. Verificar que el repositorio es llamado una vez con valor verdadero
    @Test
    public void whenFindByIndependentActivityTrue_thenRepositoryMethodCalledOnce() {
        // given
        ClientEntity client = new ClientEntity(null, "John Doe", "12345678-9", "john@example.com", "123456789", 30, 2000.0, 10000.0, "Software Engineer", 50000.0, 12, 2.5, "Personal Loan", false, 5, "Developer");
        given(clientRepository.findByIndependentActivity(true)).willReturn(client);

        // when
        clientService.findByIndependentActivity(true);

        // then
        verify(clientRepository, times(1)).findByIndependentActivity(true);
    }

    // 6. Verificar que el repositorio es llamado una vez con valor falso
    @Test
    public void whenFindByIndependentActivityFalse_thenRepositoryMethodCalledOnce() {
        // given
        ClientEntity client = new ClientEntity(null, "Jane Doe", "98765432-1", "jane@example.com", "987654321", 28, 3000.0, 15000.0, "Designer", 60000.0, 24, 3.0, "Mortgage", true, 8, "Senior Designer");
        given(clientRepository.findByIndependentActivity(false)).willReturn(client);

        // when
        clientService.findByIndependentActivity(false);

        // then
        verify(clientRepository, times(1)).findByIndependentActivity(false);
    }

    // 7. Prueba que el método funcione con diferentes instancias de cliente
    @Test
    public void whenFindByIndependentActivity_thenReturnCorrectClientBasedOnActivity() {
        // given
        ClientEntity independentClient = new ClientEntity(null, "Alice", "12345678-0", "alice@example.com", "123456780", 35, 2500.0, 12000.0, "Consultant", 55000.0, 10, 3.5, "Business Loan", false, 3, "Manager");
        given(clientRepository.findByIndependentActivity(true)).willReturn(independentClient);

        // when
        ClientEntity foundClient = clientService.findByIndependentActivity(true);

        // then
        assertThat(foundClient).isNotNull();
        assertThat(foundClient.getName()).isEqualTo("Alice");
    }
    // 1. Prueba cuando se busca un cliente con edad válida
    @Test
    public void whenFindByAge_thenReturnClient() {
        // given
        int age = 30;
        ClientEntity client = new ClientEntity(null, "John Doe", "12345678-9", "john@example.com", "123456789", age, 2000.0, 10000.0, "Software Engineer", 50000.0, 12, 2.5, "Personal Loan", false, 5, "Developer");
        given(clientRepository.findByAge(age)).willReturn(client);

        // when
        ClientEntity foundClient = clientService.findByAge(age);

        // then
        assertThat(foundClient).isNotNull();
        assertThat(foundClient.getAge()).isEqualTo(age);
    }

    // 2. Prueba cuando no hay cliente con la edad especificada
    @Test
    public void whenFindByAge_thenReturnNullIfNotFound() {
        // given
        int age = 40;
        given(clientRepository.findByAge(age)).willReturn(null);

        // when
        ClientEntity foundClient = clientService.findByAge(age);

        // then
        assertThat(foundClient).isNull();
    }

    // 3. Prueba cuando la edad es negativa
    @Test
    public void whenFindByNegativeAge_thenReturnNull() {
        // given
        int negativeAge = -5;

        // when
        ClientEntity foundClient = clientService.findByAge(negativeAge);

        // then
        assertThat(foundClient).isNull();
    }

    // 4. Prueba cuando la edad es cero
    @Test
    public void whenFindByAgeZero_thenReturnNullIfNotFound() {
        // given
        int age = 0;
        given(clientRepository.findByAge(age)).willReturn(null);

        // when
        ClientEntity foundClient = clientService.findByAge(age);

        // then
        assertThat(foundClient).isNull();
    }

    // 5. Verificar que el repositorio es llamado una vez
    @Test
    public void whenFindByAge_thenRepositoryMethodCalledOnce() {
        // given
        int age = 30;
        ClientEntity client = new ClientEntity(null, "John Doe", "12345678-9", "john@example.com", "123456789", age, 2000.0, 10000.0, "Software Engineer", 50000.0, 12, 2.5, "Personal Loan", false, 5, "Developer");
        given(clientRepository.findByAge(age)).willReturn(client);

        // when
        clientService.findByAge(age);

        // then
        verify(clientRepository, times(1)).findByAge(age);
    }

    // 6. Prueba que el método funcione con diferentes edades
    @Test
    public void whenFindByAge_thenReturnCorrectClientBasedOnAge() {
        // given
        int age = 25;
        ClientEntity client = new ClientEntity(null, "Alice", "12345678-0", "alice@example.com", "123456780", age, 2500.0, 12000.0, "Consultant", 55000.0, 10, 3.5, "Business Loan", false, 3, "Manager");
        given(clientRepository.findByAge(age)).willReturn(client);

        // when
        ClientEntity foundClient = clientService.findByAge(age);

        // then
        assertThat(foundClient).isNotNull();
        assertThat(foundClient.getAge()).isEqualTo(age);
    }

    // 7. Prueba cuando se busca con edad no válida
    @Test
    public void whenFindByInvalidAge_thenReturnNull() {
        // given
        int age = 150; // Supongamos que 150 es un valor poco probable
        given(clientRepository.findByAge(age)).willReturn(null);

        // when
        ClientEntity foundClient = clientService.findByAge(age);

        // then
        assertThat(foundClient).isNull();
    }

    // 1. Prueba cuando se busca un cliente con un salario válido
    @Test
    public void whenFindByMonthlySalary_thenReturnClient() {
        // given
        double salary = 2000.0;
        ClientEntity client = new ClientEntity(null, "John Doe", "12345678-9", "john@example.com", "123456789", 30, salary, 10000.0, "Software Engineer", 50000.0, 12, 2.5, "Personal Loan", false, 5, "Developer");
        given(clientRepository.findByMonthlySalary(salary)).willReturn(client);

        // when
        ClientEntity foundClient = clientService.findByMonthlySalary(salary);

        // then
        assertThat(foundClient).isNotNull();
        assertThat(foundClient.getMonthly_salary()).isEqualTo(salary);
    }

    // 2. Prueba cuando no hay cliente con el salario especificado
    @Test
    public void whenFindByMonthlySalary_thenReturnNullIfNotFound() {
        // given
        double salary = 4000.0;
        given(clientRepository.findByMonthlySalary(salary)).willReturn(null);

        // when
        ClientEntity foundClient = clientService.findByMonthlySalary(salary);

        // then
        assertThat(foundClient).isNull();
    }

    // 3. Prueba cuando el salario es negativo
    @Test
    public void whenFindByNegativeSalary_thenReturnNull() {
        // given
        double negativeSalary = -1500.0;

        // when
        ClientEntity foundClient = clientService.findByMonthlySalary(negativeSalary);

        // then
        assertThat(foundClient).isNull();
    }

    // 4. Prueba cuando el salario es cero
    @Test
    public void whenFindBySalaryZero_thenReturnNullIfNotFound() {
        // given
        double salary = 0.0;
        given(clientRepository.findByMonthlySalary(salary)).willReturn(null);

        // when
        ClientEntity foundClient = clientService.findByMonthlySalary(salary);

        // then
        assertThat(foundClient).isNull();
    }

    // 5. Verificar que el repositorio es llamado una vez
    @Test
    public void whenFindBySalary_thenRepositoryMethodCalledOnce() {
        // given
        double salary = 2000.0;
        ClientEntity client = new ClientEntity(null, "John Doe", "12345678-9", "john@example.com", "123456789", 30, salary, 10000.0, "Software Engineer", 50000.0, 12, 2.5, "Personal Loan", false, 5, "Developer");
        given(clientRepository.findByMonthlySalary(salary)).willReturn(client);

        // when
        clientService.findByMonthlySalary(salary);

        // then
        verify(clientRepository, times(1)).findByMonthlySalary(salary);
    }

    // 6. Prueba que el método funcione con diferentes salarios
    @Test
    public void whenFindBySalary_thenReturnCorrectClientBasedOnSalary() {
        // given
        double salary = 3000.0;
        ClientEntity client = new ClientEntity(null, "Alice", "12345678-0", "alice@example.com", "123456780", 25, salary, 12000.0, "Consultant", 55000.0, 10, 3.5, "Business Loan", false, 3, "Manager");
        given(clientRepository.findByMonthlySalary(salary)).willReturn(client);

        // when
        ClientEntity foundClient = clientService.findByMonthlySalary(salary);

        // then
        assertThat(foundClient).isNotNull();
        assertThat(foundClient.getMonthly_salary()).isEqualTo(salary);
    }

    // 7. Prueba cuando se busca con un salario no válido (por ejemplo, muy alto)
    @Test
    public void whenFindByUnrealisticallyHighSalary_thenReturnNull() {
        // given
        double salary = 1000000.0; // Un valor muy alto
        given(clientRepository.findByMonthlySalary(salary)).willReturn(null);

        // when
        ClientEntity foundClient = clientService.findByMonthlySalary(salary);

        // then
        assertThat(foundClient).isNull();
    }

    // 1. Prueba cuando se busca un cliente con ahorros válidos
    @Test
    public void whenFindByPersonalSavings_thenReturnClient() {
        // given
        Double savings = 5000.0;
        ClientEntity client = new ClientEntity(null, "John Doe", "12345678-9", "john@example.com", "123456789", 30, 2000.0, savings, "Software Engineer", 50000.0, 12, 2.5, "Personal Loan", false, 5, "Developer");
        given(clientRepository.findByPersonalSavings(savings)).willReturn(client);

        // when
        ClientEntity foundClient = clientService.findByPersonalSavings(savings);

        // then
        assertThat(foundClient).isNotNull();
        assertThat(foundClient.getPersonal_savings()).isEqualTo(savings);
    }

    // 2. Prueba cuando no se encuentra un cliente con los ahorros especificados
    @Test
    public void whenFindByPersonalSavings_thenReturnNullIfNotFound() {
        // given
        Double savings = 7000.0;
        given(clientRepository.findByPersonalSavings(savings)).willReturn(null);

        // when
        ClientEntity foundClient = clientService.findByPersonalSavings(savings);

        // then
        assertThat(foundClient).isNull();
    }

    // 3. Prueba cuando los ahorros son nulos
    @Test
    public void whenFindByNullSavings_thenReturnNull() {
        // given
        given(clientRepository.findByPersonalSavings(null)).willReturn(null);

        // when
        ClientEntity foundClient = clientService.findByPersonalSavings(null);

        // then
        assertThat(foundClient).isNull();
    }

    // 4. Prueba cuando los ahorros son negativos
    @Test
    public void whenFindByNegativeSavings_thenReturnNull() {
        // given
        Double negativeSavings = -1000.0;

        // when
        ClientEntity foundClient = clientService.findByPersonalSavings(negativeSavings);

        // then
        assertThat(foundClient).isNull();
    }

    // 5. Verificar que el repositorio es llamado una vez
    @Test
    public void whenFindByPersonalSavings_thenRepositoryMethodCalledOnce() {
        // given
        Double savings = 3000.0;
        ClientEntity client = new ClientEntity(null, "John Doe", "12345678-9", "john@example.com", "123456789", 30, 2000.0, savings, "Software Engineer", 50000.0, 12, 2.5, "Personal Loan", false, 5, "Developer");
        given(clientRepository.findByPersonalSavings(savings)).willReturn(client);

        // when
        clientService.findByPersonalSavings(savings);

        // then
        verify(clientRepository, times(1)).findByPersonalSavings(savings);
    }

    // 6. Prueba con un valor alto de ahorros
    @Test
    public void whenFindByHighPersonalSavings_thenReturnClient() {
        // given
        Double highSavings = 100000.0;
        ClientEntity client = new ClientEntity(null, "Alice", "98765432-1", "alice@example.com", "987654321", 35, 3500.0, highSavings, "Project Manager", 80000.0, 24, 3.0, "Mortgage", true, 10, "Lead Manager");
        given(clientRepository.findByPersonalSavings(highSavings)).willReturn(client);

        // when
        ClientEntity foundClient = clientService.findByPersonalSavings(highSavings);

        // then
        assertThat(foundClient).isNotNull();
        assertThat(foundClient.getPersonal_savings()).isEqualTo(highSavings);
    }

    // 7. Prueba cuando se busca con un ahorro de cero
    @Test
    public void whenFindByZeroSavings_thenReturnClient() {
        // given
        Double zeroSavings = 0.0;
        ClientEntity client = new ClientEntity(null, "Jane Doe", "87654321-0", "jane@example.com", "876543210", 28, 4000.0, zeroSavings, "Designer", 70000.0, 18, 2.0, "Business Loan", true, 6, "Designer Lead");
        given(clientRepository.findByPersonalSavings(zeroSavings)).willReturn(client);

        // when
        ClientEntity foundClient = clientService.findByPersonalSavings(zeroSavings);

        // then
        assertThat(foundClient).isNotNull();
        assertThat(foundClient.getPersonal_savings()).isEqualTo(zeroSavings);
    }

    // 1. Prueba cuando se busca un cliente con monto esperado válido
    @Test
    public void whenFindByExpectedAmount_thenReturnClient() {
        // given
        double expectedAmount = 50000.0;
        ClientEntity client = new ClientEntity(1L, "John Doe", "12345678-9", "john@example.com", "123456789", 30, 2000.0, 10000.0, "Software Engineer", expectedAmount, 12, 2.5, "Personal Loan", false, 5, "Developer");
        given(clientRepository.findByExpectedAmount(expectedAmount)).willReturn(client);

        // when
        ClientEntity foundClient = clientService.findByExpectedAmount(expectedAmount);

        // then
        assertThat(foundClient).isNotNull();
        assertThat(foundClient.getExpected_amount()).isEqualTo(expectedAmount);
    }

    // 2. Prueba cuando no se encuentra un cliente con el monto esperado especificado
    @Test
    public void whenFindByExpectedAmount_thenReturnNullIfNotFound() {
        // given
        double expectedAmount = 70000.0;
        given(clientRepository.findByExpectedAmount(expectedAmount)).willReturn(null);

        // when
        ClientEntity foundClient = clientService.findByExpectedAmount(expectedAmount);

        // then
        assertThat(foundClient).isNull();
    }

    // 3. Prueba cuando el monto esperado es cero
    @Test
    public void whenFindByZeroExpectedAmount_thenReturnClient() {
        // given
        double zeroExpectedAmount = 0.0;
        ClientEntity client = new ClientEntity(2L, "Jane Doe", "98765432-1", "jane@example.com", "987654321", 28, 3000.0, 15000.0, "Designer", zeroExpectedAmount, 24, 3.0, "Mortgage", true, 8, "Senior Designer");
        given(clientRepository.findByExpectedAmount(zeroExpectedAmount)).willReturn(client);

        // when
        ClientEntity foundClient = clientService.findByExpectedAmount(zeroExpectedAmount);

        // then
        assertThat(foundClient).isNotNull();
        assertThat(foundClient.getExpected_amount()).isEqualTo(zeroExpectedAmount);
    }

    // 4. Prueba cuando el monto esperado es negativo
    @Test
    public void whenFindByNegativeExpectedAmount_thenReturnNull() {
        // given
        double negativeExpectedAmount = -1000.0;

        // when
        ClientEntity foundClient = clientService.findByExpectedAmount(negativeExpectedAmount);

        // then
        assertThat(foundClient).isNull();
    }

    // 5. Verificar que el repositorio es llamado una vez
    @Test
    public void whenFindByExpectedAmount_thenRepositoryMethodCalledOnce() {
        // given
        double expectedAmount = 30000.0;
        ClientEntity client = new ClientEntity(1L, "John Doe", "12345678-9", "john@example.com", "123456789", 30, 2000.0, 10000.0, "Software Engineer", expectedAmount, 12, 2.5, "Personal Loan", false, 5, "Developer");
        given(clientRepository.findByExpectedAmount(expectedAmount)).willReturn(client);

        // when
        clientService.findByExpectedAmount(expectedAmount);

        // then
        verify(clientRepository, times(1)).findByExpectedAmount(expectedAmount);
    }

    // 6. Prueba con un monto esperado alto
    @Test
    public void whenFindByHighExpectedAmount_thenReturnClient() {
        // given
        double highExpectedAmount = 100000.0;
        ClientEntity client = new ClientEntity(3L, "Alice", "98765432-1", "alice@example.com", "987654321", 35, 3500.0, 25000.0, "Project Manager", highExpectedAmount, 24, 3.0, "Business Loan", true, 10, "Lead Manager");
        given(clientRepository.findByExpectedAmount(highExpectedAmount)).willReturn(client);

        // when
        ClientEntity foundClient = clientService.findByExpectedAmount(highExpectedAmount);

        // then
        assertThat(foundClient).isNotNull();
        assertThat(foundClient.getExpected_amount()).isEqualTo(highExpectedAmount);
    }

    // 7. Prueba con un monto esperado bajo
    @Test
    public void whenFindByLowExpectedAmount_thenReturnClient() {
        // given
        double lowExpectedAmount = 500.0;
        ClientEntity client = new ClientEntity(4L, "Bob", "56789012-3", "bob@example.com", "567890123", 25, 1500.0, 5000.0, "Junior Developer", lowExpectedAmount, 6, 1.5, "Car Loan", false, 2, "Developer");
        given(clientRepository.findByExpectedAmount(lowExpectedAmount)).willReturn(client);

        // when
        ClientEntity foundClient = clientService.findByExpectedAmount(lowExpectedAmount);

        // then
        assertThat(foundClient).isNotNull();
        assertThat(foundClient.getExpected_amount()).isEqualTo(lowExpectedAmount);
    }

    // 1. Prueba cuando se busca un cliente con un límite de tiempo válido
    @Test
    public void whenFindByTimeLimit_thenReturnClient() {
        // given
        int timeLimit = 12;
        ClientEntity client = new ClientEntity(1L, "John Doe", "12345678-9", "john@example.com", "123456789", 30, 2000.0, 10000.0, "Software Engineer", 50000.0, timeLimit, 2.5, "Personal Loan", false, 5, "Developer");
        given(clientRepository.findByTimeLimit(timeLimit)).willReturn(client);

        // when
        ClientEntity foundClient = clientService.findByTimeLimit(timeLimit);

        // then
        assertThat(foundClient).isNotNull();
        assertThat(foundClient.getTime_limit()).isEqualTo(timeLimit);
    }

    // 2. Prueba cuando no se encuentra un cliente con el límite de tiempo especificado
    @Test
    public void whenFindByTimeLimit_thenReturnNullIfNotFound() {
        // given
        int timeLimit = 24;
        given(clientRepository.findByTimeLimit(timeLimit)).willReturn(null);

        // when
        ClientEntity foundClient = clientService.findByTimeLimit(timeLimit);

        // then
        assertThat(foundClient).isNull();
    }

    // 3. Prueba cuando el límite de tiempo es cero
    @Test
    public void whenFindByZeroTimeLimit_thenReturnClient() {
        // given
        int zeroTimeLimit = 0;
        ClientEntity client = new ClientEntity(2L, "Jane Doe", "98765432-1", "jane@example.com", "987654321", 28, 3000.0, 15000.0, "Designer", 60000.0, zeroTimeLimit, 3.0, "Mortgage", true, 8, "Senior Designer");
        given(clientRepository.findByTimeLimit(zeroTimeLimit)).willReturn(client);

        // when
        ClientEntity foundClient = clientService.findByTimeLimit(zeroTimeLimit);

        // then
        assertThat(foundClient).isNotNull();
        assertThat(foundClient.getTime_limit()).isEqualTo(zeroTimeLimit);
    }

    // 4. Prueba cuando el límite de tiempo es negativo
    @Test
    public void whenFindByNegativeTimeLimit_thenReturnNull() {
        // given
        int negativeTimeLimit = -1;

        // when
        ClientEntity foundClient = clientService.findByTimeLimit(negativeTimeLimit);

        // then
        assertThat(foundClient).isNull();
    }

    // 5. Verificar que el repositorio es llamado una vez
    @Test
    public void whenFindByTimeLimit_thenRepositoryMethodCalledOnce() {
        // given
        int timeLimit = 18;
        ClientEntity client = new ClientEntity(3L, "Alice", "45678912-3", "alice@example.com", "456789123", 35, 4000.0, 20000.0, "Project Manager", 75000.0, timeLimit, 2.0, "Business Loan", true, 10, "Lead Manager");
        given(clientRepository.findByTimeLimit(timeLimit)).willReturn(client);

        // when
        clientService.findByTimeLimit(timeLimit);

        // then
        verify(clientRepository, times(1)).findByTimeLimit(timeLimit);
    }

    // 6. Prueba con un límite de tiempo alto
    @Test
    public void whenFindByHighTimeLimit_thenReturnClient() {
        // given
        int highTimeLimit = 48;
        ClientEntity client = new ClientEntity(4L, "Bob", "67890123-4", "bob@example.com", "678901234", 40, 5000.0, 30000.0, "Senior Developer", 90000.0, highTimeLimit, 1.8, "Car Loan", false, 12, "Architect");
        given(clientRepository.findByTimeLimit(highTimeLimit)).willReturn(client);

        // when
        ClientEntity foundClient = clientService.findByTimeLimit(highTimeLimit);

        // then
        assertThat(foundClient).isNotNull();
        assertThat(foundClient.getTime_limit()).isEqualTo(highTimeLimit);
    }

    // 7. Prueba con un límite de tiempo bajo
    @Test
    public void whenFindByLowTimeLimit_thenReturnClient() {
        // given
        int lowTimeLimit = 6;
        ClientEntity client = new ClientEntity(5L, "Charlie", "78901234-5", "charlie@example.com", "789012345", 22, 2500.0, 8000.0, "Junior Developer", 35000.0, lowTimeLimit, 2.1, "Student Loan", false, 2, "Intern");
        given(clientRepository.findByTimeLimit(lowTimeLimit)).willReturn(client);

        // when
        ClientEntity foundClient = clientService.findByTimeLimit(lowTimeLimit);

        // then
        assertThat(foundClient).isNotNull();
        assertThat(foundClient.getTime_limit()).isEqualTo(lowTimeLimit);
    }

    // 1. Prueba cuando se busca un cliente con una tasa de interés válida
    @Test
    public void whenFindByInterestRate_thenReturnClient() {
        // given
        double interestRate = 2.5;
        ClientEntity client = new ClientEntity(1L, "John Doe", "12345678-9", "john@example.com", "123456789", 30, 2000.0, 10000.0, "Software Engineer", 50000.0, 12, interestRate, "Personal Loan", false, 5, "Developer");
        given(clientRepository.findByInterestRate(interestRate)).willReturn(client);

        // when
        ClientEntity foundClient = clientService.findByInterestRate(interestRate);

        // then
        assertThat(foundClient).isNotNull();
        assertThat(foundClient.getInterest_rate()).isEqualTo(interestRate);
    }

    // 2. Prueba cuando no se encuentra un cliente con la tasa de interés especificada
    @Test
    public void whenFindByInterestRate_thenReturnNullIfNotFound() {
        // given
        double interestRate = 3.0;
        given(clientRepository.findByInterestRate(interestRate)).willReturn(null);

        // when
        ClientEntity foundClient = clientService.findByInterestRate(interestRate);

        // then
        assertThat(foundClient).isNull();
    }

    // 3. Prueba cuando la tasa de interés es cero
    @Test
    public void whenFindByZeroInterestRate_thenReturnClient() {
        // given
        double zeroInterestRate = 0.0;
        ClientEntity client = new ClientEntity(2L, "Jane Doe", "98765432-1", "jane@example.com", "987654321", 28, 3000.0, 15000.0, "Designer", 60000.0, 24, zeroInterestRate, "Mortgage", true, 8, "Senior Designer");
        given(clientRepository.findByInterestRate(zeroInterestRate)).willReturn(client);

        // when
        ClientEntity foundClient = clientService.findByInterestRate(zeroInterestRate);

        // then
        assertThat(foundClient).isNotNull();
        assertThat(foundClient.getInterest_rate()).isEqualTo(zeroInterestRate);
    }

    // 4. Prueba cuando la tasa de interés es negativa
    @Test
    public void whenFindByNegativeInterestRate_thenReturnNull() {
        // given
        double negativeInterestRate = -1.5;

        // when
        ClientEntity foundClient = clientService.findByInterestRate(negativeInterestRate);

        // then
        assertThat(foundClient).isNull();
    }

    // 5. Verificar que el repositorio es llamado una vez
    @Test
    public void whenFindByInterestRate_thenRepositoryMethodCalledOnce() {
        // given
        double interestRate = 4.5;
        ClientEntity client = new ClientEntity(3L, "Alice", "45678912-3", "alice@example.com", "456789123", 35, 4000.0, 20000.0, "Project Manager", 75000.0, 36, interestRate, "Business Loan", true, 10, "Lead Manager");
        given(clientRepository.findByInterestRate(interestRate)).willReturn(client);

        // when
        clientService.findByInterestRate(interestRate);

        // then
        verify(clientRepository, times(1)).findByInterestRate(interestRate);
    }

    // 6. Prueba con una tasa de interés alta
    @Test
    public void whenFindByHighInterestRate_thenReturnClient() {
        // given
        double highInterestRate = 10.0;
        ClientEntity client = new ClientEntity(4L, "Bob", "67890123-4", "bob@example.com", "678901234", 40, 5000.0, 30000.0, "Senior Developer", 90000.0, 48, highInterestRate, "Car Loan", false, 12, "Architect");
        given(clientRepository.findByInterestRate(highInterestRate)).willReturn(client);

        // when
        ClientEntity foundClient = clientService.findByInterestRate(highInterestRate);

        // then
        assertThat(foundClient).isNotNull();
        assertThat(foundClient.getInterest_rate()).isEqualTo(highInterestRate);
    }

    // 7. Prueba con una tasa de interés baja
    @Test
    public void whenFindByLowInterestRate_thenReturnClient() {
        // given
        double lowInterestRate = 1.0;
        ClientEntity client = new ClientEntity(5L, "Charlie", "78901234-5", "charlie@example.com", "789012345", 22, 2500.0, 8000.0, "Junior Developer", 35000.0, 6, lowInterestRate, "Student Loan", false, 2, "Intern");
        given(clientRepository.findByInterestRate(lowInterestRate)).willReturn(client);

        // when
        ClientEntity foundClient = clientService.findByInterestRate(lowInterestRate);

        // then
        assertThat(foundClient).isNotNull();
        assertThat(foundClient.getInterest_rate()).isEqualTo(lowInterestRate);
    }

    // 1. Prueba para guardar un cliente válido
    @Test
    public void whenSaveValidClient_thenReturnSavedClient() {
        // given
        ClientEntity client = new ClientEntity(null, "John Doe", "12345678-9", "john@example.com", "123456789", 30, 2000.0, 10000.0, "Software Engineer", 50000.0, 12, 2.5, "Personal Loan", false, 5, "Developer");
        given(clientRepository.save(client)).willReturn(client);

        // when
        ClientEntity savedClient = clientService.save(client);

        // then
        assertThat(savedClient).isNotNull();
        assertThat(savedClient.getName()).isEqualTo("John Doe");
    }

    // 2. Prueba para guardar un cliente con un nombre nulo
    @Test
    public void whenSaveClientWithNullName_thenReturnClient() {
        // given
        ClientEntity client = new ClientEntity(null, null, "12345678-9", "john@example.com", "123456789", 30, 2000.0, 10000.0, "Software Engineer", 50000.0, 12, 2.5, "Personal Loan", false, 5, "Developer");
        given(clientRepository.save(client)).willReturn(client);

        // when
        ClientEntity savedClient = clientService.save(client);

        // then
        assertThat(savedClient).isNotNull();
        assertThat(savedClient.getName()).isNull();
    }

    // 3. Prueba para guardar un cliente con una tasa de interés negativa
    @Test
    public void whenSaveClientWithNegativeInterestRate_thenReturnClient() {
        // given
        ClientEntity client = new ClientEntity(null, "Jane Doe", "98765432-1", "jane@example.com", "987654321", 28, 3000.0, 15000.0, "Designer", 60000.0, 24, -3.0, "Mortgage", true, 8, "Senior Designer");
        given(clientRepository.save(client)).willReturn(client);

        // when
        ClientEntity savedClient = clientService.save(client);

        // then
        assertThat(savedClient).isNotNull();
        assertThat(savedClient.getInterest_rate()).isEqualTo(-3.0);
    }

    // 4. Prueba para verificar que el método save del repositorio es invocado una vez
    @Test
    public void whenSaveClient_thenRepositoryMethodCalledOnce() {
        // given
        ClientEntity client = new ClientEntity(null, "Bob Smith", "45678912-3", "bob@example.com", "456789123", 35, 4000.0, 20000.0, "Project Manager", 75000.0, 36, 3.5, "Business Loan", false, 10, "Manager");
        given(clientRepository.save(client)).willReturn(client);

        // when
        clientService.save(client);

        // then
        verify(clientRepository, times(1)).save(client);
    }

    // 5. Prueba para guardar un cliente con ahorros nulos
    @Test
    public void whenSaveClientWithNullSavings_thenReturnClient() {
        // given
        ClientEntity client = new ClientEntity(null, "Charlie Brown", "78901234-5", "charlie@example.com", "789012345", 22, 2500.0, null, "Junior Developer", 35000.0, 6, 1.0, "Student Loan", false, 2, "Intern");
        given(clientRepository.save(client)).willReturn(client);

        // when
        ClientEntity savedClient = clientService.save(client);

        // then
        assertThat(savedClient).isNotNull();
        assertThat(savedClient.getPersonal_savings()).isNull();
    }

    // 6. Prueba para guardar un cliente con todos los campos nulos excepto la identificación
    @Test
    public void whenSaveClientWithNullFieldsExceptId_thenReturnClient() {
        // given
        ClientEntity client = new ClientEntity(1L, null, null, null, null, 0, 0.0, null, null, 0.0, 0, 0.0, null, false, 0, null);
        given(clientRepository.save(client)).willReturn(client);

        // when
        ClientEntity savedClient = clientService.save(client);

        // then
        assertThat(savedClient).isNotNull();
        assertThat(savedClient.getClient_id()).isEqualTo(1L);
    }

    // 7. Prueba para guardar un cliente con todos los campos correctamente llenados
    @Test
    public void whenSaveCompleteClient_thenReturnSavedClient() {
        // given
        ClientEntity client = new ClientEntity(2L, "Alice Wonderland", "11111111-1", "alice@example.com", "111111111", 40, 6000.0, 25000.0, "Engineer", 70000.0, 36, 3.0, "Car Loan", true, 15, "Principal Engineer");
        given(clientRepository.save(client)).willReturn(client);

        // when
        ClientEntity savedClient = clientService.save(client);

        // then
        assertThat(savedClient).isNotNull();
        assertThat(savedClient.getClient_id()).isEqualTo(2L);
        assertThat(savedClient.getName()).isEqualTo("Alice Wonderland");
    }

    // 1. Prueba para eliminar un cliente con un ID válido
    @Test
    public void whenDeleteByIdWithValidId_thenRepositoryMethodCalledOnce() {
        // given
        Long clientId = 1L;

        // when
        clientService.deleteById(clientId);

        // then
        verify(clientRepository, times(1)).deleteById(clientId);
    }

    // 2. Prueba para eliminar un cliente con un ID nulo
    @Test
    public void whenDeleteByIdWithNullId_thenRepositoryMethodNotCalled() {
        // when
        clientService.deleteById(null);

        // then
        verify(clientRepository, never()).deleteById(null);
    }

    // 3. Prueba para eliminar un cliente con un ID inexistente
    @Test
    public void whenDeleteByIdWithNonExistentId_thenRepositoryMethodCalled() {
        // given
        Long nonExistentId = 999L;

        // when
        clientService.deleteById(nonExistentId);

        // then
        verify(clientRepository, times(1)).deleteById(nonExistentId);
    }

    // 4. Prueba para verificar que el método deleteById no lanza excepciones
    @Test
    public void whenDeleteById_thenNoExceptionThrown() {
        // given
        Long clientId = 2L;

        // when
        clientService.deleteById(clientId);

        // then
        verify(clientRepository, times(1)).deleteById(clientId);
    }

    // 5. Prueba para eliminar múltiples IDs seguidos
    @Test
    public void whenDeleteByIdMultipleTimes_thenRepositoryMethodCalledMultipleTimes() {
        // given
        Long clientId1 = 1L;
        Long clientId2 = 2L;

        // when
        clientService.deleteById(clientId1);
        clientService.deleteById(clientId2);

        // then
        verify(clientRepository, times(1)).deleteById(clientId1);
        verify(clientRepository, times(1)).deleteById(clientId2);
    }

    // 6. Prueba para asegurarse que deleteById no haga nada cuando se le pasa un ID negativo
    @Test
    public void whenDeleteByIdWithNegativeId_thenRepositoryMethodNotCalled() {
        // given
        Long negativeId = -1L;

        // when
        clientService.deleteById(negativeId);

        // then
        verify(clientRepository, never()).deleteById(negativeId);
    }

    // 7. Prueba para eliminar un cliente cuando el ID es 0
    @Test
    public void whenDeleteByIdWithZeroId_thenRepositoryMethodNotCalled() {
        // given
        Long zeroId = 0L;

        // when
        clientService.deleteById(zeroId);

        // then
        verify(clientRepository, never()).deleteById(zeroId);
    }


    // 1. Prueba para eliminar todos los clientes
    @Test
    public void whenDeleteAll_thenRepositoryMethodCalledOnce() {
        // when
        clientService.deleteAll();

        // then
        verify(clientRepository, times(1)).deleteAll();
    }

    // 2. Prueba para verificar que deleteAll no lanza excepciones
    @Test
    public void whenDeleteAll_thenNoExceptionThrown() {
        // when
        clientService.deleteAll();

        // then
        verify(clientRepository, times(1)).deleteAll();
    }

    // 3. Prueba para eliminar todos los clientes sin registros
    @Test
    public void whenDeleteAllWithNoClients_thenRepositoryMethodCalled() {
        // given
        doNothing().when(clientRepository).deleteAll();

        // when
        clientService.deleteAll();

        // then
        verify(clientRepository, times(1)).deleteAll();
    }

    // 4. Prueba para asegurarse de que deleteAll se puede ejecutar varias veces
    @Test
    public void whenDeleteAllCalledMultipleTimes_thenRepositoryMethodCalledMultipleTimes() {
        // when
        clientService.deleteAll();
        clientService.deleteAll();

        // then
        verify(clientRepository, times(2)).deleteAll();
    }

    // 5. Prueba para verificar que deleteAll no hace llamadas adicionales si no hay registros
    @Test
    public void whenDeleteAllCalledWithEmptyDatabase_thenRepositoryMethodStillCalled() {
        // given
        doNothing().when(clientRepository).deleteAll();

        // when
        clientService.deleteAll();

        // then
        verify(clientRepository, times(1)).deleteAll();
    }

    // 6. Prueba para eliminar todos los clientes, asegurándose que el método se llama sin importar la cantidad de registros
    @Test
    public void whenDeleteAllWithAnyNumberOfClients_thenRepositoryMethodCalled() {
        // when
        clientService.deleteAll();

        // then
        verify(clientRepository, times(1)).deleteAll();
    }

    // 7. Prueba para verificar que deleteAll no afecta otras operaciones en el servicio
    @Test
    public void whenDeleteAll_thenOtherServiceMethodsUnaffected() {
        // when
        clientService.deleteAll();

        // Call some other method (mocked for this test)
        clientService.findAll();

        // then
        verify(clientRepository, times(1)).deleteAll();
        verify(clientRepository, times(1)).findAll();  // assuming findAll() is mocked
    }

    // 1. Prueba cuando el cliente existe
    @Test
    public void whenExistsByIdWithExistingId_thenReturnTrue() {
        // given
        Long existingId = 1L;
        given(clientRepository.existsById(existingId)).willReturn(true);

        // when
        boolean exists = clientService.existsById(existingId);

        // then
        assertThat(exists).isTrue();
        verify(clientRepository, times(1)).existsById(existingId);
    }

    // 2. Prueba cuando el cliente no existe
    @Test
    public void whenExistsByIdWithNonExistingId_thenReturnFalse() {
        // given
        Long nonExistingId = 100L;
        given(clientRepository.existsById(nonExistingId)).willReturn(false);

        // when
        boolean exists = clientService.existsById(nonExistingId);

        // then
        assertThat(exists).isFalse();
        verify(clientRepository, times(1)).existsById(nonExistingId);
    }

    // 3. Prueba cuando el ID es nulo
    @Test
    public void whenExistsByIdWithNullId_thenReturnFalse() {
        // given
        given(clientRepository.existsById(null)).willReturn(false);

        // when
        boolean exists = clientService.existsById(null);

        // then
        assertThat(exists).isFalse();
        verify(clientRepository, times(1)).existsById(null);
    }

    // 4. Prueba cuando el ID es negativo
    @Test
    public void whenExistsByIdWithNegativeId_thenReturnFalse() {
        // given
        Long negativeId = -1L;
        given(clientRepository.existsById(negativeId)).willReturn(false);

        // when
        boolean exists = clientService.existsById(negativeId);

        // then
        assertThat(exists).isFalse();
        verify(clientRepository, times(1)).existsById(negativeId);
    }

    // 5. Prueba cuando el ID es cero
    @Test
    public void whenExistsByIdWithZeroId_thenReturnFalse() {
        // given
        Long zeroId = 0L;
        given(clientRepository.existsById(zeroId)).willReturn(false);

        // when
        boolean exists = clientService.existsById(zeroId);

        // then
        assertThat(exists).isFalse();
        verify(clientRepository, times(1)).existsById(zeroId);
    }

    // 6. Prueba para verificar que el repositorio se llama solo una vez
    @Test
    public void whenExistsById_thenRepositoryMethodCalledOnce() {
        // given
        Long clientId = 1L;
        given(clientRepository.existsById(clientId)).willReturn(true);

        // when
        clientService.existsById(clientId);

        // then
        verify(clientRepository, times(1)).existsById(clientId);
    }

    // 7. Prueba cuando se pasa un valor máximo de Long
    @Test
    public void whenExistsByIdWithMaxLongValue_thenReturnFalse() {
        // given
        Long maxLongId = Long.MAX_VALUE;
        given(clientRepository.existsById(maxLongId)).willReturn(false);

        // when
        boolean exists = clientService.existsById(maxLongId);

        // then
        assertThat(exists).isFalse();
        verify(clientRepository, times(1)).existsById(maxLongId);
    }

    // 1. Prueba cuando el RUT existe
    @Test
    public void whenExistsByRutWithExistingRut_thenReturnTrue() {
        // given
        String existingRut = "12345678-9";
        given(clientRepository.existsByRut(existingRut)).willReturn(true);

        // when
        boolean exists = clientService.existsByRut(existingRut);

        // then
        assertThat(exists).isTrue();
        verify(clientRepository, times(1)).existsByRut(existingRut);
    }

    // 2. Prueba cuando el RUT no existe
    @Test
    public void whenExistsByRutWithNonExistingRut_thenReturnFalse() {
        // given
        String nonExistingRut = "98765432-1";
        given(clientRepository.existsByRut(nonExistingRut)).willReturn(false);

        // when
        boolean exists = clientService.existsByRut(nonExistingRut);

        // then
        assertThat(exists).isFalse();
        verify(clientRepository, times(1)).existsByRut(nonExistingRut);
    }

    // 3. Prueba cuando el RUT es nulo
    @Test
    public void whenExistsByRutWithNullRut_thenReturnFalse() {
        // given
        given(clientRepository.existsByRut(null)).willReturn(false);

        // when
        boolean exists = clientService.existsByRut(null);

        // then
        assertThat(exists).isFalse();
        verify(clientRepository, times(1)).existsByRut(null);
    }

    // 4. Prueba cuando el RUT es una cadena vacía
    @Test
    public void whenExistsByRutWithEmptyRut_thenReturnFalse() {
        // given
        String emptyRut = "";
        given(clientRepository.existsByRut(emptyRut)).willReturn(false);

        // when
        boolean exists = clientService.existsByRut(emptyRut);

        // then
        assertThat(exists).isFalse();
        verify(clientRepository, times(1)).existsByRut(emptyRut);
    }

    // 5. Prueba cuando el RUT tiene espacios
    @Test
    public void whenExistsByRutWithSpaces_thenReturnFalse() {
        // given
        String rutWithSpaces = " 12345678-9 ";
        String trimmedRut = rutWithSpaces.trim();
        given(clientRepository.existsByRut(trimmedRut)).willReturn(false);

        // when
        boolean exists = clientService.existsByRut(trimmedRut);

        // then
        assertThat(exists).isFalse();
        verify(clientRepository, times(1)).existsByRut(trimmedRut);
    }

    // 6. Prueba cuando el RUT tiene un formato inválido
    @Test
    public void whenExistsByRutWithInvalidFormat_thenReturnFalse() {
        // given
        String invalidRut = "1234-5678";
        given(clientRepository.existsByRut(invalidRut)).willReturn(false);

        // when
        boolean exists = clientService.existsByRut(invalidRut);

        // then
        assertThat(exists).isFalse();
        verify(clientRepository, times(1)).existsByRut(invalidRut);
    }

    // 7. Prueba para verificar que el repositorio se llama solo una vez
    @Test
    public void whenExistsByRut_thenRepositoryMethodCalledOnce() {
        // given
        String rut = "12345678-9";
        given(clientRepository.existsByRut(rut)).willReturn(true);

        // when
        clientService.existsByRut(rut);

        // then
        verify(clientRepository, times(1)).existsByRut(rut);
    }

    // 1. Prueba cuando el cliente es nulo
    @Test
    public void whenUpdateWithNullClient_thenReturnNull() {
        // when
        ClientEntity updatedClient = clientService.update(null);

        // then
        assertThat(updatedClient).isNull();
        verify(clientRepository, times(0)).save(null);
    }

    // 2. Prueba cuando el cliente tiene ID nulo
    @Test
    public void whenUpdateWithClientHavingNullId_thenReturnNull() {
        // given
        ClientEntity client = new ClientEntity();
        client.setClient_id(null); // ID es nulo

        // when
        ClientEntity updatedClient = clientService.update(client);

        // then
        assertThat(updatedClient).isNull();
        verify(clientRepository, times(0)).save(client);
    }

    // 3. Prueba cuando se actualiza un cliente válido
    @Test
    public void whenUpdateWithValidClient_thenReturnUpdatedClient() {
        // given
        ClientEntity client = new ClientEntity(
                1L, "John Doe", "12345678-9", "john@example.com",
                "123456789", 30, 2000.0, 10000.0,
                "Software Engineer", 50000.0, 12,
                2.5, "Personal Loan", false, 5, "Developer"
        );

        given(clientRepository.save(client)).willReturn(client);

        // when
        ClientEntity updatedClient = clientService.update(client);

        // then
        assertThat(updatedClient).isEqualTo(client);
        verify(clientRepository, times(1)).save(client);
    }

    // 4. Prueba cuando se intenta actualizar un cliente existente
    @Test
    public void whenUpdateExistingClient_thenSaveIsCalledOnce() {
        // given
        ClientEntity client = new ClientEntity(2L, "Jane Doe", "87654321-0", "jane@example.com",
                "987654321", 28, 2500.0, 5000.0,
                "Data Analyst", 60000.0, 24,
                3.0, "Home Loan", true, 3, "Analyst");

        // when
        clientService.update(client);

        // then
        verify(clientRepository, times(1)).save(client);
    }

    // 5. Prueba cuando se actualiza un cliente sin cambios
    @Test
    public void whenUpdateClientWithNoChanges_thenSaveIsCalledOnce() {
        // given
        ClientEntity client = new ClientEntity(3L, "Alice Smith", "12312312-3", "alice@example.com",
                "456789123", 35, 3000.0, 15000.0,
                "Manager", 70000.0, 36,
                4.0, "Business Loan", false, 10, "Manager");

        given(clientRepository.save(client)).willReturn(client);

        // when
        ClientEntity updatedClient = clientService.update(client);

        // then
        assertThat(updatedClient).isEqualTo(client);
        verify(clientRepository, times(1)).save(client);
    }

    // 6. Prueba cuando se actualiza un cliente con datos inválidos
    @Test
    public void whenUpdateClientWithInvalidData_thenSaveIsNotCalled() {
        // given
        ClientEntity client = new ClientEntity(4L, "", "45645645-4", "invalid@example.com",
                "987654321", 20, 1500.0, 500.0,
                "Intern", 10000.0, 6,
                0.5, "Personal Loan", false, 1, "Intern");

        // when
        ClientEntity updatedClient = clientService.update(client);

        // then
        assertThat(updatedClient).isNull();
        verify(clientRepository, times(0)).save(client);
    }

    // 7. Prueba cuando se actualiza un cliente con nombre que contiene un número
    @Test
    public void whenUpdateClientNameContainsNumber_thenReturnUpdatedClient() {
        // given
        ClientEntity client = new ClientEntity(
                5L, "John Doe2", "12345678-9", "john2@example.com",
                "123456789", 30, 2000.0, 10000.0,
                "Software Engineer", 50000.0, 12,
                2.5, "Personal Loan", false, 5, "Developer"
        );

        given(clientRepository.save(client)).willReturn(client);

        // when
        ClientEntity updatedClient = clientService.update(client);

        // then
        assertThat(updatedClient).isEqualTo(client);
        assertThat(updatedClient.getName()).contains("2");
        verify(clientRepository, times(1)).save(client);
    }



}








