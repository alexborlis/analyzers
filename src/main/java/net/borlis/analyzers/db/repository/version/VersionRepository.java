package net.borlis.analyzers.db.repository.version;

import net.borlis.analyzers.db.model.VersionModel;
import net.borlis.analyzers.db.repository.AbstractDao;
import org.springframework.stereotype.Repository;

import java.util.Collection;

/**
 * Created by user on 06.10.15.
 */
@Repository("versionRepository")
public class VersionRepository extends AbstractDao<String, VersionModel> implements VersionDao {


    public VersionModel findById(String id) {
        return null;
    }

    public void save(VersionModel object) {

    }

    public Collection<VersionModel> findAll() {
        return null;
    }

    public VersionModel update(VersionModel object) {
        return null;
    }
}
