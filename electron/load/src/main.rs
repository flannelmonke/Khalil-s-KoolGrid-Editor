use std::env;
use std::fs;
use std::path::Path;
fn main() {
    let args: Vec<String> = env::args().collect();
    let path = &args[1];
    let mut input = std::fs::read_to_string(path).unwrap();

    let template = format!(
        "<!DOCTYPE html>
        <html>
            <head>
                <title> {} </title>
                    <style>
                    input {{
                        border: 1px solid black;
                        padding: 0px;
                        margin: 0px;

                    }}</style>
            </head>
            <body>
                <table>",
        path
    );

    let end = "</table>
            <script src='../file_scripts/renderer.js'></script>
            </body>
        </html>";

    input = input
        .lines()
        .map(|line: &str| {
            let cells = line
                .split(",")
                .map(|cell| format!("<td><input type='text' value='{}' oninput='updateValue(this)'></td> \n", cell))
                .collect::<Vec<_>>()
                .join("");
            format!("<tr id='poo'> \n {}</tr> \n", cells)
        })
        .collect::<Vec<_>>()
        .join("");

    let contents = format!("{}{}{}", template, input, end);

    let target_path = "./templates/index.html";
    let target_dir = Path::new(target_path).parent().unwrap();

    fs::create_dir_all(target_dir).expect("error creating directory");
    fs::write("../file_index.txt", path).expect("error writing file");
    let result = fs::write(target_path, contents).expect("error writing file");
    if result == () {
        println!("File written to {}", target_path);
    } else {
        println!("Error writing file");
    }
}
