import 'package:flutter/material.dart';

void main() {
  runApp(MyApp());
}

class MyApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: TaskList(),
    );
  }
}

class TaskList extends StatefulWidget {
  @override
  _TaskListState createState() => _TaskListState();
}

class _TaskListState extends State<TaskList> {
  List<Map<String, String>> tasks = [];

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('Задачи'),
        actions: [
          IconButton(
            icon: Icon(Icons.add),
            onPressed: () {
              _addNewTask(context);
            },
          ),
        ],
      ),
      body: ListView.builder(
        itemCount: tasks.length,
        itemBuilder: (context, index) {
          bool isDisabled = index % 2 != 0;

          return ListTile(
            title: Text('Задача: ${tasks[index]['title']}'),
            subtitle: Text('Описание: ${tasks[index]['description']}'),
            leading: Icon(
              isDisabled ? Icons.check_box_outline_blank : Icons.check_box,
              color: isDisabled ? Colors.grey : Colors.blue,
            ),
            enabled: !isDisabled,
            trailing: Icon(Icons.arrow_forward_ios),
            onTap: isDisabled
                ? null
                : () {
                    _showTaskDetails(context, index);
                  },
          );
        },
      ),
    );
  }

  void _showTaskDetails(BuildContext context, int index) {
    showDialog(
      context: context,
      builder: (BuildContext context) {
        return AlertDialog(
          title: Text('Задача: ${tasks[index]['title']}'),
          content: Text('Описание: ${tasks[index]['description']}'),
          actions: <Widget>[
            TextButton(
              child: Text('Закрыть'),
              onPressed: () {
                Navigator.of(context).pop();
              },
            ),
          ],
        );
      },
    );
  }

  void _addNewTask(BuildContext context) {
    final TextEditingController titleController = TextEditingController();
    final TextEditingController descriptionController = TextEditingController();

    showDialog(
      context: context,
      builder: (BuildContext context) {
        return AlertDialog(
          title: Text('Добавить задачу'),
          content: Column(
            crossAxisAlignment: CrossAxisAlignment.start,
            mainAxisSize: MainAxisSize.min,
            children: <Widget>[
              TextField(
                controller: titleController,
                decoration: InputDecoration(labelText: 'Название задачи'),
              ),
              TextField(
                controller: descriptionController,
                decoration: InputDecoration(labelText: 'Описание задачи'),
              ),
            ],
          ),
          actions: <Widget>[
            TextButton(
              child: Text('Отменить'),
              onPressed: () {
                Navigator.of(context).pop();
              },
            ),
            TextButton(
              child: Text('Добавить'),
              onPressed: () {
                setState(() {
                  tasks.add({
                    'title': titleController.text,
                    'description': descriptionController.text,
                  });
                });
                Navigator.of(context).pop();
              },
            ),
          ],
        );
      },
    );
  }
}
