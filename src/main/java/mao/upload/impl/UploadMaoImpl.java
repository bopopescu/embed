package mao.upload.impl;

import core.annotation.Mao;
import mao.upload.IUploadMao;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.DatastoreImpl;

/**
 * Created by digvijaysharma on 18/12/16.
 */
@Mao
public class UploadMaoImpl implements IUploadMao {

    private Datastore store;

    public UploadMaoImpl(DatastoreImpl store) {
        this.store = store;
    }

}
