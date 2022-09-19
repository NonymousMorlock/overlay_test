import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:url_launcher/url_launcher.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({Key? key}) : super(key: key);


  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Flutter Demo',
      theme: ThemeData(
        primarySwatch: Colors.blue,
      ),
      home: const MyHomePage(title: 'Flutter Demo Home Page'),
    );
  }
}

class MyHomePage extends StatefulWidget {
  const MyHomePage({Key? key, required this.title}) : super(key: key);

  final String title;

  @override
  State<MyHomePage> createState() => _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> {

  static const channel = MethodChannel("co.akundadababalei/slider");

  Future showOverlay() async {
    channel.invokeMethod("showOverlay");

    const lat = "1.3528368";
    const lng = "-147.6127292";

    Uri uri = Uri.parse("google.navigation:q=$lat,$lng&mode=d");

    if(!await launchUrl(uri)) throw UnsupportedError("could not access google maps app");

  }


  @override
  Widget build(BuildContext context) {

    return Scaffold(
      appBar: AppBar(
        title: Text(widget.title),
      ),
      body: Center(
        child: ElevatedButton(
          onPressed: showOverlay,
          child: const Text("Go to Google Maps"),
        ),
      ),
    );
  }
}
