package space.moontalk.mc.iman.command.sub;

import org.checkerframework.checker.nullness.qual.NonNull;

import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor
public class Subcommand {
    @NonNull
    String name;
}
