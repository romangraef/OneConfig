package cc.polyfrost.oneconfig.utils.commands.annotations;

import cc.polyfrost.oneconfig.utils.commands.CommandManager;
import cc.polyfrost.oneconfig.utils.commands.arguments.ArgumentParser;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Marks a class as a command.
 * <p>
 * To start, create a class which is annotated with this annotation, and then a method which is annotated with {@link Main}.
 * <pre>{@code
 *     @literal @Command(name = "test", description = "A test command", aliases = {"t"})
 *     public class TestCommand {
 *         @literal @Main
 *         public static void mainCommandMethod() {
 *             // do stuff
 *         }
 *     }
 *     }</pre>
 * <p>
 * Keep in mind how {@code mainCommandMethod} is a private and static method.
 * With OneConfig's command utility, methods for commands can be any kind of visibility, and <b><i>must be static</i></b>.
 * If the methods are not static, the {@link CommandManager} will throw an exception.
 *
 * <p>
 * Command methods can also having multiple parameters of virtually any type, as long as it is a
 * {@link String}, {@code boolean}, {@code int}, {@code double}, {@code float},
 * or is added as an {@link ArgumentParser} and added to the
 * {@link CommandManager} via {@link CommandManager#addParser(ArgumentParser)}.
 * Parameters can also be annotated with various annotations, such as {@link Name}, which names the parameter
 * for the user, {@link Greedy}, which takes all following arguments along with itself, and more to come.
 * For example, the following command method:
 * <pre>{@code
 *     @literal @Main
 *     private static void mainCommandMethod(String arg1, @Name("nameOfSecondArgument") boolean arg2, int arg3, double arg4, float arg5, @Greedy String greedyArgument) {
 *         // do things here
 *         System.out.println(greedyArgument); // Greedily takes all remaining arguments after greedyArgument as well as itself. 1984
 *     }
 *     }</pre>
 * </p>
 *
 * <p>
 * Of course, {@link SubCommand}s can be added and "stacked". For example, the following command class:
 * <pre>{@code
 *     @literal @Command(name = "mycommand", aliases = {"alias1"}, description = "My command description")
 *     public class TestCommand {
 *         @literal @Main
 *         private static void mainCommandMethod() {
 *             // do things here
 *         }
 *
 *         @literal @SubCommand(name = "subcommand", aliases = {"subalias1"}, description = "My subcommand description")
 *         private static class SubCommandClass {
 *             @literal @Main
 *             private static void subCommandMethod() {
 *                 // do things here
 *             }
 *
 *             @literal @SubCommand(name = "subsubcommand", aliases = {"subsubalias1"}, description = "My subsubcommand description")
 *             private static class SubSubCommandClass {
 *                 @literal @Main
 *                 private static void subSubCommandMethod() {
 *                     // do things here
 *                 }
 *             }
 *         }
 *     }
 *     }</pre>
 * </p>
 * <p>
 * To register commands, use {@link CommandManager#registerCommand(Class)}.
 *
 * <p>
 * Note: if you're viewing this in IntelliJ or just see the @literal tag everywhere, please ignore that.
 * </p>
 *
 * @see cc.polyfrost.oneconfig.command.OneConfigCommand
 * @see Main
 * @see CommandManager
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface Command {
    /**
     * The name of the command.
     *
     * @return The name of the command.
     */
    String value();

    /**
     * The aliases of the command.
     *
     * @return The aliases of the command.
     */
    String[] aliases() default {};

    /**
     * The description of the command.
     *
     * @return The description of the command.
     */
    String description() default "";

    /**
     * Whether the command generates a help command.
     */
    boolean helpCommand() default true;
}
