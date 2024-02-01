// import { load_file } from "./file_scripts/open_file.js";
const { app, BrowserWindow, dialog, Menu } = require("electron");

function load_file(Path, BrowserWindow, dialog) {
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

const menuTemplate = [
  {
    label: "File",
    submenu: [
      {
        label: "Open File",
        click: () => {
          dialog
            .showOpenDialog(BrowserWindow.getFocusedWindow(), {
              properties: ["openFile"],
            })
            .then((result) => {
              if (!result.canceled) {
                let file = result.filePaths[0];
                load_file(file, BrowserWindow, dialog);
              }
            })
            .catch((err) => {
              console.log(err);
            });
        },
      },
      // other File menu items go here
    ],
  },
  // other top-level menu items go here
];

const menu = Menu.buildFromTemplate(menuTemplate);
Menu.setApplicationMenu(menu);

const createWindow = () => {
  const win = new BrowserWindow({
    width: 800,
    height: 600,
  });

  win.loadFile("index.html");
};

app.whenReady().then(() => {
  createWindow();

  app.on("activate", () => {
    if (BrowserWindow.getAllWindows().length === 0) createWindow();
  });
});
app.on("window-all-closed", () => {
  if (process.platform !== "darwin") app.quit();
});
