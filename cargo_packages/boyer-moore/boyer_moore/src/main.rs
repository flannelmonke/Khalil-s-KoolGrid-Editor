mod search;

use std::env;
use std::fs;
fn main() {
    let args: Vec<String> = env::args().collect();
    let pattern = &args[1];
    let input = &args[2];
    let text = fs::read_to_string(input).expect("File not found");
    let result = search::search(pattern.to_string(), text);
    println!("Result: {:?}", result);
}
