fn main() {
    println!("Simple conversion tool initiated");
    println!("Please enter the temperature in Fahrenheit: ");
    loop {
        let mut fahrenheit = String::new();
        std::io::stdin()
            .read_line(&mut fahrenheit)
            .expect("Failed to read line");
        match fahrenheit.trim().parse::<f32>() {
            Ok(num) => {
                let celcius: f32 = (num - 32.0) * 5.0 / 9.0;
                println!("The temperature in Celsius is: {}", celcius);
                break;
            }
            Err(_) => {
                println!("Please enter a number!");
                continue;
            }
        };
    }
}
