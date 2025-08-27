package magicsweepy.iota.optic;

import com.github.bsideup.jabel.Desugar;
import org.jetbrains.annotations.TestOnly;
import org.jspecify.annotations.NonNull;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestOnly
public final class TestLens
{

    @Desugar
    record Person(String name, int age) {}

    static final Lens<Person, Person, String, String> nameLens = new Lens<>()
    {

        @NonNull
        @Override
        public String view(@NonNull Person person)
        {
            return person.name();
        }

        @NonNull
        @Override
        public Person update(@NonNull String name, @NonNull Person person)
        {
            return new Person(name, person.age());
        }

    };

    @Test
    public void viewLensAndUpdate()
    {
        Person alice = new Person("Alice", 16);
        assertEquals("Alice", nameLens.view(alice));

        Person bob = nameLens.update("Bob", alice);
        assertEquals("Bob", bob.name());
        assertEquals(16, bob.age());
    }

}
