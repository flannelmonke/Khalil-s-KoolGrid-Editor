use std::env;
use std::fs;
use std::path::Path;
fn main() {
    let args: Vec<String> = env::args().collect();
    let path = &args[1];
    //print path
    print!("Path: {}", path);
    let template = "<!DOCTYPE html><html><head><title>My Title</title></head><body><table>";
    let end = "</table></body></html>";
    let contents = format!(
        "{}{}{}",
        template,
        std::fs::read_to_string(path).unwrap(),
        end
    );

    let target_path =
        "D:/Productivity/Stat Machomp/Khalil's KoolGrid Editor/electron/templates/index.html";
    let target_dir = Path::new(target_path).parent().unwrap();

    fs::create_dir_all(target_dir).expect("error creating directory");

    let result = fs::write(target_path, contents).expect("error writing file");
    if result == () {
        print!("File written to {}", target_path);
    } else {
        print!("Error writing file");
    }
}
