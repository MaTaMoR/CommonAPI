package me.matamor.commonapi.storage.entries;

import lombok.AllArgsConstructor;
import lombok.Getter;
import me.matamor.commonapi.storage.DataProvider;
import me.matamor.commonapi.storage.data.PlayerData;

import java.util.logging.Level;

@AllArgsConstructor
public class RegisteredData<T extends DataEntry> {

    @Getter
    private final PlayerData parent;

    @Getter
    private final T dataEntry;

    @Getter
    private final DataProvider<T> dataProvider;

    public void save() {
        try {
            this.dataProvider.getStorage().save(this.dataEntry);
        } catch (Exception e) {
            this.parent.getHandler().getPlugin().getLogger().log(Level.SEVERE, String.format("There was an error while saving the data %s from %s:",
                    this.parent.getIdentifier().toString(), this.dataEntry.getClass().getSimpleName()), e);
        }
    }

    public void saveAsync() {
        this.dataProvider.getStorage().saveAsync(this.dataEntry, (result, exception) -> {
            if (exception != null) {
                this.parent.getHandler().getPlugin().getLogger().log(Level.SEVERE, String.format("There was an error while saving the data %s from %s:",
                        this.parent.getIdentifier().toString(), this.dataEntry.getClass().getSimpleName()), exception);
            }
        });
    }
}
