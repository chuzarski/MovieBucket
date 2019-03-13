package net.chuzarski.moviebucket.db;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Transaction;

import net.chuzarski.moviebucket.models.GenreModel;

import java.util.List;

@Dao
public interface GenreDao {

    @Query("SELECT id, name FROM genres")
    LiveData<List<GenreModel>> getAllGenres();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    @Transaction
    void insertAll(List<GenreModel> models);
}
