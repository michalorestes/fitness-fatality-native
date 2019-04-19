package com.example.fitnessfatality.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.fitnessfatality.data.dao.ExerciseDao
import com.example.fitnessfatality.data.dao.WorkoutExerciseDao
import com.example.fitnessfatality.data.models.exercise.Exercise
import com.example.fitnessfatality.data.models.exercise.ExerciseType
import com.example.fitnessfatality.data.models.logging.LoggingType
import com.example.fitnessfatality.data.models.workout.MuscleGroup
import com.example.fitnessfatality.data.models.workout.WorkoutExercise
import com.example.fitnessfatality.data.typeConverter.ExerciseTypeConverter
import com.example.fitnessfatality.data.typeConverter.LoggingTypeConverter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [Exercise::class, WorkoutExercise::class], version = 13, exportSchema = false)
@TypeConverters(ExerciseTypeConverter::class, LoggingTypeConverter::class)
abstract class AppDatabase: RoomDatabase() {
    abstract fun exerciseDao(): ExerciseDao
    abstract fun workoutExerciseDao(): WorkoutExerciseDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): AppDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(context.applicationContext, AppDatabase::class.java,"database")
                    .addCallback(DatabaseCallback(scope))
                    .fallbackToDestructiveMigration()
                    .build()

                INSTANCE = instance
                return instance
            }
        }
    }

    private class DatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onOpen(db: SupportSQLiteDatabase) {
            super.onOpen(db)
            INSTANCE?.let { database ->
                scope.launch(Dispatchers.IO) {
                    populateDatabase(
                        database.exerciseDao(),
                        database.workoutExerciseDao()
                    )
                }
            }
        }

        fun populateDatabase(
            exerciseDao: ExerciseDao,
            workoutExerciseDao: WorkoutExerciseDao
        ) {
            workoutExerciseDao.deleteAll()
            exerciseDao.deleteAll()

            val exercise = Exercise(
                0,
                "Bench Press",
                ExerciseType.WEIGHTS,
                MuscleGroup.CHEST,
                listOf(MuscleGroup.TRICEPS),
                false
            )

            exerciseDao.insert(exercise)

            val exercise2 = Exercise(
                1,
                "Barbel Squat",
                ExerciseType.WEIGHTS,
                MuscleGroup.HAMSTRINGS,
                listOf(MuscleGroup.ABS),
                false
            )

            exerciseDao.insert(exercise2)

            val exercise3 = Exercise(
                2,
                "Biceps Curl",
                ExerciseType.WEIGHTS,
                MuscleGroup.BICEPS,
                listOf(MuscleGroup.FOREARMS),
                false
            )

            exerciseDao.insert(exercise3)

            val workoutExercise = WorkoutExercise(
                0,
                0,
                0,
                0,
                hashMapOf("sets" to 1, "reps" to 2),
                LoggingType.WEIGHTS
            )

            val workoutExercise2 = WorkoutExercise(
                1,
                0,
                1,
                1,
                hashMapOf("sets" to 1, "reps" to 2),
                LoggingType.WEIGHTS
            )

            val workoutExercise3 = WorkoutExercise(
                2,
                0,
                2,
                2,
                hashMapOf("sets" to 1, "reps" to 2),
                LoggingType.WEIGHTS
            )

            workoutExerciseDao.insert(workoutExercise)
            workoutExerciseDao.insert(workoutExercise2)
            workoutExerciseDao.insert(workoutExercise3)
        }
    }
}
