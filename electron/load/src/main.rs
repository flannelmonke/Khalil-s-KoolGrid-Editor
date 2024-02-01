use std::env;
use std::fs;
use std::path::Path;
fn main() {
    let args: Vec<String> = env::args().collect();
    let path = &args[1];
    //print path
    print!("Path: {}", path);
    let template = "<!DOCTYPE html>
        <html>
            <head>
                <title>My Title</title>
                    <style>
                    td {
                        border: 1px solid black;
                        padding: 8px;
                    }</style>
            </head>
            <body>
                <table>";
    let end = "</table></body></html>";
    let mut input = std::fs::read_to_string(path).unwrap();
    input = input
        .lines()
        .map(|line| format!("<tr>{}</tr>", line))
        .collect::<String>();

    input = input
        .split(",")
        .map(|line| format!("<td>{}</td>", line))
        .collect::<String>();

    println!("{}", input);
    let contents = format!(
        "{}{}{}",
        template,
        input
            .lines()
            .map(|line| format!("<tr><td>{}</td></tr>", line))
            .collect::<String>(),
        end
    );

    let target_path = "../electron/templates/index.html";
    let target_dir = Path::new(target_path).parent().unwrap();

    fs::create_dir_all(target_dir).expect("error creating directory");

    let result = fs::write(target_path, contents).expect("error writing file");
    if result == () {
        println!("File written to {}", target_path);
    } else {
        println!("Error writing file");
    }
}
