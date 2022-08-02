package space.moontalk.mc.iman.command.sub;

import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor
public class ArgsRange {
    int low;
    int high;

    public boolean contains(int val) {
        return low <= val && val <= high;
    }
}
