package dao;

import exception.NotFoundException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class RepositoryTest {

    private Repository<Long,TestDto> sut;

    @Mock
    public IUniqueGenerator<Long> longIUniqueGenerator;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setUp() {
        sut = new Repository<>(longIUniqueGenerator);
    }

    @Test
    public void get_shouldReturnValue() {
        Long id = 123L;
        when(longIUniqueGenerator.getNext()).thenReturn(id);

        TestDto expectedValue = new TestDto();

        sut.add(expectedValue);

        assertEquals(expectedValue, sut.get(id));
    }

    @Test
    public void add_shouldAddValue() {
        Long expectedId = 123L;
        when(longIUniqueGenerator.getNext()).thenReturn(expectedId);

        TestDto expectedValue = new TestDto();

        TestDto actual = sut.add(expectedValue);

        assertEquals(expectedValue, actual);
    }

    @Test
    public void update() {
        Long id = 123L;
        when(longIUniqueGenerator.getNext()).thenReturn(id);

        String oldString = "testValue";
        String newExpectedString = "newValue";

        TestDto dto = new TestDto();
        dto.setValue(oldString);

        sut.add(dto);

        TestDto expectedDto = new TestDto();
        expectedDto.setValue(newExpectedString);

        TestDto updatedValue =sut.update(id, expectedDto);

        assertEquals(expectedDto, updatedValue);
        assertEquals(newExpectedString, updatedValue.value);
    }

    @Test
    public void delete() {
        Long id = 123L;
        when(longIUniqueGenerator.getNext()).thenReturn(id);

        TestDto expectedValue = new TestDto();

        sut.add(expectedValue);

        assertNotNull(sut.get(id));
        sut.delete(id);

        expectedException.expect(NotFoundException.class);
        expectedException.expectMessage("Element with id 123 was not found in repository");
        sut.get(id);
    }

    private static class TestDto implements IKeyedValue<Long> {

        private Long id;
        private String value;

        @Override
        public Long getId() {
            return id;
        }

        @Override
        public void setId(Long key) {
            id = key;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }
}