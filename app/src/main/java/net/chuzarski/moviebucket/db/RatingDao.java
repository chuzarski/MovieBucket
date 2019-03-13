package net.chuzarski.moviebucket.db;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Transaction;

import net.chuzarski.moviebucket.models.RatingModel;

import java.util.List;

@Dao
public interface RatingDao {

    @Query("SELECT * FROM ratings ORDER BY rating_order ASC")
    LiveData<List<RatingModel>> getAllRatings();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    @Transaction
    void insertAllRatings(List<RatingModel> models);
}
