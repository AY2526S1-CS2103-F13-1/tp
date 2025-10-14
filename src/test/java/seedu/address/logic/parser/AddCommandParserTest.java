package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.DOB_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.DOB_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.HEIGHT_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.HEIGHT_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ADDRESS_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.ROLE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.ROLE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.SCHOOL_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.SCHOOL_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_HEIGHT_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ROLE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SCHOOL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_WEIGHT_BOB;
import static seedu.address.logic.commands.CommandTestUtil.WEIGHT_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.WEIGHT_DESC_BOB;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_HEIGHT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SCHOOL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_WEIGHT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalPersons.AMY;
import static seedu.address.testutil.TypicalPersons.BOB;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.AddCommand;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.PersonBuilder;

public class AddCommandParserTest {

    private final AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Person expectedPerson = new PersonBuilder(BOB)
                .withTags(VALID_TAG_FRIEND)
                .build();

        // whitespace preamble
        assertParseSuccess(parser,
                PREAMBLE_WHITESPACE + NAME_DESC_BOB + DOB_DESC_BOB + PHONE_DESC_BOB
                        + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + SCHOOL_DESC_BOB + ROLE_DESC_BOB
                        + HEIGHT_DESC_BOB + WEIGHT_DESC_BOB + TAG_DESC_FRIEND,
                new AddCommand(expectedPerson));

        // multiple tags
        Person expectedMultipleTags = new PersonBuilder(BOB)
                .withTags(VALID_TAG_FRIEND, VALID_TAG_HUSBAND)
                .build();
        assertParseSuccess(parser,
                NAME_DESC_BOB + DOB_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                        + ADDRESS_DESC_BOB + SCHOOL_DESC_BOB + ROLE_DESC_BOB + HEIGHT_DESC_BOB
                        + WEIGHT_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                new AddCommand(expectedMultipleTags));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags
        Person expectedPerson = new PersonBuilder(AMY).withTags().build();
        assertParseSuccess(parser,
                NAME_DESC_AMY + DOB_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY
                        + SCHOOL_DESC_AMY + ROLE_DESC_AMY + HEIGHT_DESC_AMY + WEIGHT_DESC_AMY,
                new AddCommand(expectedPerson));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser,
                VALID_NAME_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + SCHOOL_DESC_BOB + ROLE_DESC_BOB
                        + HEIGHT_DESC_BOB + WEIGHT_DESC_BOB,
                expectedMessage);

        // missing school prefix
        assertParseFailure(parser,
                NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + VALID_SCHOOL_BOB + ROLE_DESC_BOB
                        + HEIGHT_DESC_BOB + WEIGHT_DESC_BOB,
                expectedMessage);

        // missing role prefix
        assertParseFailure(parser,
                NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + SCHOOL_DESC_BOB + VALID_ROLE_BOB
                        + HEIGHT_DESC_BOB + WEIGHT_DESC_BOB,
                expectedMessage);

        // missing height prefix
        assertParseFailure(parser,
                NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + SCHOOL_DESC_BOB + ROLE_DESC_BOB
                        + VALID_HEIGHT_BOB + WEIGHT_DESC_BOB,
                expectedMessage);

        // missing weight prefix
        assertParseFailure(parser,
                NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + SCHOOL_DESC_BOB + ROLE_DESC_BOB
                        + HEIGHT_DESC_BOB + VALID_WEIGHT_BOB,
                expectedMessage);

        // all prefixes missing
        assertParseFailure(parser,
                VALID_NAME_BOB + VALID_PHONE_BOB + VALID_EMAIL_BOB + VALID_ADDRESS_BOB + VALID_SCHOOL_BOB
                        + VALID_ROLE_BOB + VALID_HEIGHT_BOB + VALID_WEIGHT_BOB,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser,
                INVALID_NAME_DESC + DOB_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                        + SCHOOL_DESC_BOB + ROLE_DESC_BOB + HEIGHT_DESC_BOB + WEIGHT_DESC_BOB + TAG_DESC_FRIEND,
                Name.MESSAGE_CONSTRAINTS);

        // invalid phone
        assertParseFailure(parser,
                NAME_DESC_BOB + DOB_DESC_BOB + INVALID_PHONE_DESC + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                        + SCHOOL_DESC_BOB + ROLE_DESC_BOB + HEIGHT_DESC_BOB + WEIGHT_DESC_BOB,
                Phone.MESSAGE_CONSTRAINTS);

        // invalid email
        assertParseFailure(parser,
                NAME_DESC_BOB + DOB_DESC_BOB + PHONE_DESC_BOB + INVALID_EMAIL_DESC + ADDRESS_DESC_BOB
                        + SCHOOL_DESC_BOB + ROLE_DESC_BOB + HEIGHT_DESC_BOB + WEIGHT_DESC_BOB,
                Email.MESSAGE_CONSTRAINTS);

        // invalid address
        assertParseFailure(parser,
                NAME_DESC_BOB + DOB_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + INVALID_ADDRESS_DESC
                        + SCHOOL_DESC_BOB + ROLE_DESC_BOB + HEIGHT_DESC_BOB + WEIGHT_DESC_BOB,
                Address.MESSAGE_CONSTRAINTS);

        // invalid tag
        assertParseFailure(parser,
                NAME_DESC_BOB + DOB_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                        + SCHOOL_DESC_BOB + ROLE_DESC_BOB + HEIGHT_DESC_BOB + WEIGHT_DESC_BOB
                        + INVALID_TAG_DESC + TAG_DESC_FRIEND,
                Tag.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser,
                PREAMBLE_NON_EMPTY + NAME_DESC_BOB + DOB_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                        + SCHOOL_DESC_BOB + ROLE_DESC_BOB + HEIGHT_DESC_BOB + WEIGHT_DESC_BOB,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_repeatedNonTagValue_failure() {
        String validArgs = NAME_DESC_BOB + DOB_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + SCHOOL_DESC_BOB + ROLE_DESC_BOB + HEIGHT_DESC_BOB + WEIGHT_DESC_BOB + TAG_DESC_FRIEND;

        // multiple names
        assertParseFailure(parser,
                NAME_DESC_AMY + validArgs,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // multiple roles
        assertParseFailure(parser,
                ROLE_DESC_AMY + validArgs,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ROLE));

        // multiple schools
        assertParseFailure(parser,
                SCHOOL_DESC_AMY + validArgs,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_SCHOOL));

        // multiple heights
        assertParseFailure(parser,
                HEIGHT_DESC_AMY + validArgs,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_HEIGHT));

        // multiple weights
        assertParseFailure(parser,
                WEIGHT_DESC_AMY + validArgs,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_WEIGHT));

        // multiple fields repeated
        assertParseFailure(parser,
                validArgs + NAME_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY + ROLE_DESC_AMY + SCHOOL_DESC_AMY
                        + HEIGHT_DESC_AMY + WEIGHT_DESC_AMY,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME, PREFIX_EMAIL, PREFIX_ADDRESS, PREFIX_ROLE,
                        PREFIX_SCHOOL, PREFIX_HEIGHT, PREFIX_WEIGHT));
    }
}
