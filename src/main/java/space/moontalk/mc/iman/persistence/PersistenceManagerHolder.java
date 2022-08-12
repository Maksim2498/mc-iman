package space.moontalk.mc.iman.persistence;

import org.jetbrains.annotations.NotNull;

public interface PersistenceManagerHolder {
    @NotNull PersistenceManager getPersistenceManager();
}
