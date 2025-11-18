package pl.edu.agh.school;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.name.Named;
import pl.edu.agh.logger.ConsoleMessageSerializer;
import pl.edu.agh.logger.FileMessageSerializer;
import pl.edu.agh.logger.IMessageSerializer;
import pl.edu.agh.school.persistence.IPersistenceManager;
import pl.edu.agh.school.persistence.SerializablePersistenceManager;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class SchoolModule extends AbstractModule {

    @Provides
    public IPersistenceManager providePersistenceManager(SerializablePersistenceManager persistenceManager) {
        return persistenceManager;
    }

    @Provides
    @Named("classes storage")
    public String provideClassesStorageName() {
        return "classes2.dat";
    }

    @Provides
    @Named("teachers storage")
    public String provideTeacherStorageName() {
        return "teachers2.dat";
    }

    @Provides
    public Set<IMessageSerializer> provideMessageSerializers(
            FileMessageSerializer file,
            ConsoleMessageSerializer console
    ) {
        Set<IMessageSerializer> set = new HashSet<>();
        set.add(console);
        set.add(file);
        return Collections.unmodifiableSet(set);
    }

    @Provides
    @Named("log file name")
    public String provideLogFileName() {
        return "persistence.log";
    }
}
