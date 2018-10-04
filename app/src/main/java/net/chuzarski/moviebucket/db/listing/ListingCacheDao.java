package net.chuzarski.moviebucket.db.listing;

import android.arch.paging.DataSource;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import net.chuzarski.moviebucket.models.ListingItemModel;

import java.util.List;

@Dao
public interface ListingCacheDao {

    @Query("SELECT * FROM ListingItemModel")
    List<ListingItemModel> getAll();

    @Query("SELECT * FROM ListingItemModel")
    public abstract DataSource.Factory<Integer, ListingItemModel> getAllDataSource();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<ListingItemModel> models);

}
