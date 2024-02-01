export default function load_file(Path, BrowserWindow, dialog) {
  let file = file.target.files[0];
  if (file) {
    let reader = new FileReader();
    reader.onload = function (e) {
      let contents = e.target.result;
      BrowserWindow.webContents.send("file-opened", Path, contents);
    };
    console.log("File read successfully" + file + " " + reader.result);
  } else {
    console.log("Error: File not found");
  }
}
