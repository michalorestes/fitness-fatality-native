package com.example.fitnessfatality.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.fitnessfatality.R
import com.example.fitnessfatality.data.dao.*
import com.example.fitnessfatality.data.models.exercise.Exercise
import com.example.fitnessfatality.data.models.exercise.ExerciseType
import com.example.fitnessfatality.data.models.exercise.MuscleGroup
import com.example.fitnessfatality.data.models.routine.Routine
import com.example.fitnessfatality.data.models.routine.RoutineExercise
import com.example.fitnessfatality.data.models.workoutSession.Log
import com.example.fitnessfatality.data.models.workoutSession.LogSet
import com.example.fitnessfatality.data.models.workoutSession.Workout
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
        RoutineExercise::class,
        Routine::class,
        Log::class,
        Workout::class,
        LogSet::class
    ],
    version = 31,
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
    abstract fun routineExerciseDao(): RoutineExerciseDao
    abstract fun routineDao(): RoutineDao
    abstract fun workoutDao(): WorkoutDao
    abstract fun logDao(): LogDao
    abstract fun logSetDao(): LogSetDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): AppDatabase {
//            context.deleteDatabase("database")
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "database"
                )
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
                        database.routineExerciseDao(),
                        database.routineDao(),
                        database.logDao(),
                        database.logSetDao(),
                        database.workoutDao()
                    )
                }
            }
        }

        fun populateDatabase(
            exerciseDao: ExerciseDao,
            routineExerciseDao: RoutineExerciseDao,
            routineDao: RoutineDao,
            logDao: LogDao,
            logSetDao: LogSetDao,
            workoutDao: WorkoutDao
        ) {
            logSetDao.deleteAll()
            logDao.deleteAll()
            routineExerciseDao.deleteAll()
            workoutDao.deleteAll()
            routineDao.deleteAll()
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

            val workoutExercise = RoutineExercise(
                0,
                0,
                0,
                0,
                hashMapOf("sets" to 2, "reps" to 2, "rest" to 30)
            )

            val workoutExercise2 = RoutineExercise(
                1,
                0,
                1,
                1,
                hashMapOf("sets" to 3, "reps" to 2, "rest" to 60)
            )

            val workoutExercise3 = RoutineExercise(
                3,
                0,
                2,
                2,
                hashMapOf("sets" to 1, "reps" to 2, "rest" to 50)
            )

            val workoutExercise4 = RoutineExercise(
                2,
                1,
                3,
                3,
                hashMapOf("duration" to 30, "sets" to 1, "rest" to 90)
            )

//            routineExerciseDao.insert(workoutExercise)
//            routineExerciseDao.insert(workoutExercise2)
            routineExerciseDao.insert(workoutExercise3)
            routineExerciseDao.insert(workoutExercise4)


            val routine = Routine(
                0,
                "Sample workout routine",
                R.drawable.inverval_workout_icon,
                0
            )

            routineDao.insert(routine)
            routineDao.insert(Routine(
                1,
                "Sample workout routine 2",
                R.drawable.inverval_workout_icon,
                2
            ))
            routineDao.insert(Routine(
                2,
                "Sample workout routine 3",
                R.drawable.cardio_workout_icon,
                3
            ))
            routineDao.insert(Routine(
                3,
                "Sample workout routine 4",
                R.drawable.inverval_workout_icon,
                4
            ))
            routineDao.insert(Routine(
                4,
                "Sample workout routine 5",
                R.drawable.cardio_workout_icon,
                5
            ))
            routineDao.insert(Routine(
                5,
                "Sample workout routine 6",
                R.drawable.inverval_workout_icon,
                6
            ))
        }
    }
}
