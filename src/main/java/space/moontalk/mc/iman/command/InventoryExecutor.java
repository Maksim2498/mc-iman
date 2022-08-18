package space.moontalk.mc.iman.command;

import org.jetbrains.annotations.NotNull;

import lombok.Getter;
import lombok.val;

import space.moontalk.mc.commands.RootCommandExecutor;

import space.moontalk.mc.iman.command.sub.*;

import space.moontalk.mc.iman.message.*;
import space.moontalk.mc.iman.persistence.*;

public class InventoryExecutor extends    RootCommandExecutor
                               implements MessageProviderHolder,
                                          PersistenceManagerHolder {
    @Getter
    private final @NotNull PersistenceManager persistenceManager;
                               
    public InventoryExecutor(
        @NotNull MessageProvider    errorMessageProvider,
        @NotNull PersistenceManager persistenceManager
    ) {
        super(errorMessageProvider); 

        this.persistenceManager = persistenceManager;

        setupListExecutor();
        setupSaveExecutor();
        setupSetExecutor();
        setupRemoveExecutor();
    }

    private void setupListExecutor() {
        val executor = new ListInventoriesExecutor(getMessageProvider(), getPersistenceManager());

        addSubcommandExecutor("list", executor);
        addSubcommandExecutor("ls",   executor);
    }

    private void setupSaveExecutor() {
        addSubcommandExecutor("save", new SaveInventoryExecutor(getMessageProvider(), getPersistenceManager()));
    }

    private void setupSetExecutor() {
        addSubcommandExecutor("set", new SetInventoryExecutor(getMessageProvider(), getPersistenceManager()));
    }

    private void setupRemoveExecutor() {
        val executor = new RemoveInventoryExecutor(getMessageProvider(), getPersistenceManager());

        addSubcommandExecutor("remove", executor);
        addSubcommandExecutor("rm",     executor);
    }

    @Override
    public @NotNull MessageProvider getMessageProvider() {
        return (MessageProvider) getErrorMessageProvider();
    }
}
