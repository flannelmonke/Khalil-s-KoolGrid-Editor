pub fn search(pattern: String, text: String) {
    let pattern_len = pattern.len();
    let text_len = text.len();

    let mut skip = vec![pattern_len; 256];

    for (i, c) in pattern[..pattern_len - 1].chars().enumerate() {
        skip[c as usize] = pattern_len - i - 1;
    }
    let mut i = pattern_len - 1;
    while i < text_len {
        let c = text.chars().nth(i).unwrap();
        if pattern[pattern_len - 1..].to_string()
            == String::from(text.get(i - pattern_len + 1..=i).unwrap())
        {
            println!("Match at index {}", i - pattern_len + 1);
        }
        i += skip[c as usize];
    }
}
