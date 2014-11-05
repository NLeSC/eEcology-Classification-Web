package nl.esciencecenter.mydropwizardproject.commands;

/**
 * Command pattern (see http://www.oodesign.com/command-pattern.html)
 *
 * @author christiaan
 *
 */
public interface Command<Parameters> {
    public void execute(Parameters parameters);
}
