package io.keepcoding.todo.data.repository.datasource.local

import io.keepcoding.todo.data.model.Task
import io.keepcoding.todo.data.model.mapper.TaskEntityMapper
import io.keepcoding.todo.data.model.mapper.TaskMapper
import io.keepcoding.todo.data.repository.TaskDataSource
import io.reactivex.Flowable
import io.reactivex.Single

class LocalDataSource(val todoDatabase: TodoDatabase,
                      val taskMapper: TaskMapper,
                      val taskEntitiyMapper: TaskEntityMapper) : TaskDataSource {

    override fun getAll(): Single<List<Task>> =
        todoDatabase
            .getTaskDao()
            .getAll()
            .map { taskMapper.transformList(it) }

    override fun observeAll(): Flowable<List<Task>> =
        todoDatabase
            .getTaskDao()
            .observeAll()
            .map { taskMapper.transformList(it) }

    override fun getTaskById(taskId: Long): Single<Task> =
        todoDatabase
            .getTaskDao()
            .getById(taskId)
            .map { taskMapper.transform(it) }

    override fun insert(task: Task) {
        val taskEntitiy = taskEntitiyMapper.transform(task)

        todoDatabase
            .getTaskDao()
            .insert(taskEntitiy)
    }

    override fun delete(task: Task) {
        val taskEntity = taskEntitiyMapper.transform(task)

        todoDatabase
            .getTaskDao()
            .delete(taskEntity)
    }

    override fun update(task: Task) {
        val taskEntitiy = taskEntitiyMapper.transform(task)

        todoDatabase
            .getTaskDao()
            .update(taskEntitiy)
    }

}