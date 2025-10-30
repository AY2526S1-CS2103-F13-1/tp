package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.team.Team;
import seedu.address.model.team.TeamName;
import seedu.address.model.team.session.Location;
import seedu.address.model.team.session.Session;
import seedu.address.testutil.PersonBuilder;

/**
 * Unit tests for {@link AddSessionCommand}.
 *
 * Mirrors the style of AddCommandTest/AddTeamCommandTest with Model stubs.
 */
public class AddSessionCommandTest {

    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");

    @Test
    public void constructor_nullTeamIndex_throwsNullPointerException() {
        Session s = makeSession("2025-10-21 0700", "2025-10-21 0800", "Track");
        assertThrows(NullPointerException.class, () -> new AddSessionCommand(null, s));
    }

    @Test
    public void constructor_nullSession_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddSessionCommand(Index.fromOneBased(1), null));
    }

    @Test
    public void execute_validInputs_addSuccessful() throws Exception {
        ModelStubAcceptingSessionAdded model = new ModelStubAcceptingSessionAdded();
        Team team = makeTeam("Alpha");
        model.setFilteredTeams(List.of(team));

        Session s = makeSession("2025-10-21 0700", "2025-10-21 0800", "Track");
        AddSessionCommand cmd = new AddSessionCommand(Index.fromOneBased(1), s);

        CommandResult result = cmd.execute(model);

        String expectedMessage = String.format(AddSessionCommand.MESSAGE_SUCCESS, team.getName().toString(), s);
        assertEquals(expectedMessage, result.getFeedbackToUser());
        assertTrue(model.addedTo.contains(s));
        assertFalse(result.isShowHelp());
        assertFalse(result.isExit());
        assertTrue(result.isShowTeams());
    }

    @Test
    public void execute_invalidTeamIndex_throwsCommandException() {
        ModelStubAcceptingSessionAdded model = new ModelStubAcceptingSessionAdded();
        model.setFilteredTeams(List.of()); // empty list -> any index invalid

        Session s = makeSession("2025-10-21 0700", "2025-10-21 0800", "Track");
        AddSessionCommand cmd = new AddSessionCommand(Index.fromOneBased(1), s);

        CommandException ex = assertThrows(CommandException.class, () -> cmd.execute(model));
        assertEquals(AddSessionCommand.MESSAGE_INVALID_TEAM_DISPLAYED_INDEX, ex.getMessage());
    }

    @Test
    public void execute_duplicateSession_throwsCommandException() {
        // Team test double that claims it already has the session
        Team team = new TeamDoubleWithSession("Alpha");
        ModelStubAcceptingSessionAdded model = new ModelStubAcceptingSessionAdded();
        model.setFilteredTeams(List.of(team));

        Session s = makeSession("2025-10-21 0700", "2025-10-21 0800", "Track");
        AddSessionCommand cmd = new AddSessionCommand(Index.fromOneBased(1), s);

        CommandException ex = assertThrows(CommandException.class, () -> cmd.execute(model));
        assertEquals(AddSessionCommand.MESSAGE_DUPLICATE_INPUT, ex.getMessage());
    }

    @Test
    public void equals() {
        Session s1 = makeSession("2025-10-21 0700", "2025-10-21 0800", "Track");
        Session s2 = makeSession("2025-10-22 0700", "2025-10-22 0800", "Track");
        AddSessionCommand a = new AddSessionCommand(Index.fromOneBased(1), s1);
        AddSessionCommand b = new AddSessionCommand(Index.fromOneBased(1), s1);
        AddSessionCommand c = new AddSessionCommand(Index.fromOneBased(2), s1);
        AddSessionCommand d = new AddSessionCommand(Index.fromOneBased(1), s2);

        assertTrue(a.equals(b));
        assertTrue(a.equals(a));
        assertFalse(a.equals(null));
        assertFalse(a.equals(1));
        assertFalse(a.equals(c)); // different index
        assertFalse(a.equals(d)); // different session
    }

    private static Session makeSession(String start, String end, String location) {
        LocalDateTime s = LocalDateTime.parse(start, FMT);
        LocalDateTime e = LocalDateTime.parse(end, FMT);
        return new Session(new Location(location), s, e);
    }

    private static Team makeTeam(String name) {
        TeamName tn = new TeamName(name);
        Set<Person> members = new HashSet<>();
        members.add(new PersonBuilder().withName("A1").build());
        members.add(new PersonBuilder().withName("A2").build());
        members.add(new PersonBuilder().withName("A3").build());
        members.add(new PersonBuilder().withName("A4").build());
        return new Team(tn, members);
    }


    /**
     * A default model stub that have all of the methods failing.
     */
    private static class ModelStub implements Model {
        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public GuiSettings getGuiSettings() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getAddressBookFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBookFilePath(Path addressBookFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBook(ReadOnlyAddressBook newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasIdenticalPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deletePerson(Person target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setPerson(Person target, Person editedPerson) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean isAddressBookEmpty() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredPersonList(Predicate<Person> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addTeam(Team team) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasTeam(Team target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteTeam(Team target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setTeam(Team target, Team editedTeam) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Team> getFilteredTeamList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredTeamList(Predicate<Team> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Team getTeamOfPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addSessionToTeam(Team team, Session session) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteSession(Team team, Session session) {
            throw new AssertionError("This method should not be called.");
        }
    }

    /** Model stub that records sessions added and exposes a filtered team list. */
    private static class ModelStubAcceptingSessionAdded extends ModelStub {
        private final List<Team> teams = new ArrayList<>();
        private final List<Session> addedTo = new ArrayList<>();

        void setFilteredTeams(List<Team> list) {
            teams.clear();
            teams.addAll(list);
        }

        @Override
        public ObservableList<Team> getFilteredTeamList() {
            return FXCollections.observableArrayList(teams);
        }

        @Override
        public void addSessionToTeam(Team target, Session session) {
            addedTo.add(session);
        }
    }

    /**
     * Test-double Team that always returns true for hasSession(session), and has a stable name.
     * Subclasses Team to reuse constructor invariants (4 distinct members).
     */
    private static class TeamDoubleWithSession extends Team {
        TeamDoubleWithSession(String name) {
            super(new TeamName(name), fourMembers());
        }

        private static Set<Person> fourMembers() {
            Set<Person> m = new HashSet<>();
            m.add(new PersonBuilder().withName("D1").build());
            m.add(new PersonBuilder().withName("D2").build());
            m.add(new PersonBuilder().withName("D3").build());
            m.add(new PersonBuilder().withName("D4").build());
            return m;
        }

        @Override
        public boolean hasSession(Session s) {
            return true; // simulate duplicate
        }
    }
}
