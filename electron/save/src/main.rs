use std::env;
fn main() {
    let args: Vec<String> = env::args().collect();
    let path = &args[1];
    let save_type = &args[2];
    let input = std::fs::read_to_string(path).unwrap();

    match save_type.as_str() {
        "new" => std::fs::write(path, input.as_bytes()).expect("bad things are happening"),
        "overwrite" => std::fs::write(path, input.as_bytes()).expect("bad things are happening"),
        "append" => std::fs::write(path, input.as_bytes()).expect("bad things are happening"),
        _ => println!("Invalid save type"),
    }
}
