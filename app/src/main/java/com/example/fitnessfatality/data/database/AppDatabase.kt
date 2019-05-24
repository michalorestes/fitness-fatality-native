package com.example.fitnessfatality.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.fitnessfatality.data.dao.ExerciseDao
import com.example.fitnessfatality.data.dao.ExerciseLogDao
import com.example.fitnessfatality.data.dao.WorkoutDao
import com.example.fitnessfatality.data.dao.WorkoutExerciseDao
import com.example.fitnessfatality.data.models.exercise.Exercise
import com.example.fitnessfatality.data.models.exercise.ExerciseType
import com.example.fitnessfatality.data.models.logging.ExerciseLog
import com.example.fitnessfatality.data.models.logging.LoggingType
import com.example.fitnessfatality.data.models.workout.MuscleGroup
import com.example.fitnessfatality.data.models.workout.Workout
import com.example.fitnessfatality.data.models.workout.WorkoutExercise
import com.example.fitnessfatality.data.typeConverter.ExerciseTypeConverter
import com.example.fitnessfatality.data.typeConverter.LocalDateTypeConverter
import com.example.fitnessfatality.data.typeConverter.LoggingTypeConverter
import com.example.fitnessfatality.data.typeConverter.MapTypeConverter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
    entities = [
        Exercise::class,
        WorkoutExercise::class,
        ExerciseLog::class,
        Workout::class
    ],
    version = 16,
    exportSchema = false
)
@TypeConverters(
    ExerciseTypeConverter::class,
    LoggingTypeConverter::class,
    LocalDateTypeConverter::class,
    MapTypeConverter::class
)
abstract class AppDatabase: RoomDatabase() {
    abstract fun exerciseDao(): ExerciseDao
    abstract fun workoutExerciseDao(): WorkoutExerciseDao
    abstract fun exerciseLogDao(): ExerciseLogDao
    abstract fun workoutDao(): WorkoutDao

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
                        database.workoutExerciseDao(),
                        database.exerciseLogDao()
                    )
                }
            }
        }

        fun populateDatabase(
            exerciseDao: ExerciseDao,
            workoutExerciseDao: WorkoutExerciseDao,
            exerciseLogDao: ExerciseLogDao
        ) {
            exerciseLogDao.deleteAll()
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

            val exercise4 = Exercise(
                3,
                "Trademil Running",
                ExerciseType.CARDIO,
                MuscleGroup.HAMSTRINGS,
                listOf(MuscleGroup.ABS),
                false
            )

            exerciseDao.insert(exercise4)

            val workoutExercise = WorkoutExercise(
                0,
                0,
                0,
                0,
                hashMapOf("sets" to 2, "reps" to 2, "rest" to 30),
                LoggingType.WEIGHTS
            )

            val workoutExercise2 = WorkoutExercise(
                1,
                0,
                1,
                1,
                hashMapOf("sets" to 1, "reps" to 2, "rest" to 60),
                LoggingType.WEIGHTS
            )

            val workoutExercise3 = WorkoutExercise(
                3,
                0,
                2,
                2,
                hashMapOf("sets" to 1, "reps" to 2, "rest" to 50),
                LoggingType.WEIGHTS
            )

            val workoutExercise4 = WorkoutExercise(
                2,
                0,
                3,
                3,
                hashMapOf("duration" to 30, "sets" to 1, "rest" to 90),
                LoggingType.DURATION
            )

            workoutExerciseDao.insert(workoutExercise)
            workoutExerciseDao.insert(workoutExercise2)
            workoutExerciseDao.insert(workoutExercise3)
            workoutExerciseDao.insert(workoutExercise4)
        }
    }
}
