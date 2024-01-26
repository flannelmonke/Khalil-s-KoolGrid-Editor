use chrono::offset::Local;
use std::env;

fn main() {
    let args: Vec<String> = env::args().collect();
    let command = args[1].to_string();
    match command.as_str() {
        "date" => println!("The date is: {}", Local::now().date_naive()),
        "time" => println!("The time is: {}", Local::now().time()),
        _ => println!("Please enter a valid argument!"),
    }
}
