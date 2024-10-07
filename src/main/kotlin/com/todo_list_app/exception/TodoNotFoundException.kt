package com.todo_list_app.exception

import java.lang.RuntimeException

class TodoNotFoundException(message: String) : RuntimeException(message) {

}
