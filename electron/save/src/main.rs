use regex::Regex;
// ARG Definitions
// 1: path to file to be saved (whether new or existing)
// 2: save type (overwrite : new or complete change of file,
//               save_changes: edits to existing file are saved)
fn main() {
    let path = std::fs::read_to_string("./file_index.txt").unwrap();
    let scraped_file = std::fs::read_to_string("./templates/index.html").unwrap();

    // Regex to extract the content of the cells
    let re: Regex = Regex::new(r"value='.*'").unwrap();
    let mut content: Vec<String> = Vec::new();

    // Regex to extract the rows
    let rows: Regex = Regex::new(r"<tr.*>").unwrap();
    // extract all rows
    let _row_count: i64 = rows.captures_iter(&scraped_file).count() as i64;
    let _data_count: i64 = re.captures_iter(&scraped_file).count() as i64;
    let col_count: i64 = _data_count / _row_count;
    // Extract number of columns.
    // extract the content of the cells and add line breaks

    let mut i: i64 = 0;
    for cap in re.captures_iter(&scraped_file) {
        if i == col_count-1 {
            content.push(cap[0].to_string().split("'").collect::<Vec<&str>>()[1].to_string());        
            content.push("\n".to_string());
            i = 0;
        } else {
            content.push(cap[0].to_string().split("'").collect::<Vec<&str>>()[1].to_string()+",");        
            i += 1;
        }
    }
    std::fs::write(path.clone(), content.join("").as_bytes()).expect("error writing file");
    println!("File written to {}", path);
}
