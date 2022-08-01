package space.moontalk.mc.iman.subcommand;

import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor
public class ArgumentsRange {
    int low;
    int high;

    public boolean contains(int val) {
        return low <= val && val <= high;
    }
}
