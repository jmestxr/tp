package seedu.modtrek.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.modtrek.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.modtrek.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Locale;
import java.util.Set;

import seedu.modtrek.logic.commands.TagCommand;
import seedu.modtrek.logic.parser.exceptions.ParseException;
import seedu.modtrek.model.module.Code;
import seedu.modtrek.model.tag.Tag;

/**
 * The type Tag command parser.
 */
public class TagCommandParser implements Parser<TagCommand> {
    /**
     * Parses {@code userInput} into a command and returns it.
     *
     * @param args
     * @throws ParseException if {@code userInput} does not conform the expected format
     */
    @Override
    public TagCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_TAG);

        Code code;
        boolean isInclude;

        try {
            String preamble = argMultimap.getPreamble();
            String[] preambleParts = preamble.split(" ");
            code = ParserUtil.parseCode(preambleParts[0]);
            if (preambleParts.length > 1 && preambleParts[1].toLowerCase(Locale.ROOT).equals("include")) {
                isInclude = true;
            } else if (preambleParts.length > 1 && preambleParts[1].toLowerCase(Locale.ROOT).equals("remove")) {
                isInclude = false;
            } else {
                throw new ParseException("Did not specify include/remove tags");
            }
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, TagCommand.MESSAGE_USAGE), pe);
        }

        Set<Tag> tag = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));

        return new TagCommand(code, isInclude, tag);
    }
}