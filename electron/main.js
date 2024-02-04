const { app, BrowserWindow, dialog, Menu } = require("electron");
const { spawn } = require("child_process");

// Open file dialog
function open_file() {
  dialog
    .showOpenDialog(BrowserWindow.getFocusedWindow(), {
      properties: ["openFile"],
    })
    .then((result) => {
      if (result.canceled) {
        return;
      }
      console.log(result.filePaths[0]);
      let process = spawn("./functions/load.exe", [result.filePaths[0]]);

      process.stdout.on("data", (data) => {
        console.log(`stdout: ${data}`);
      });

      process.stderr.on("data", (data) => {
        console.error(`stderr: ${data}`);
      });

      process.on("close", (code) => {
        if (code !== 0) {
          console.log(`process exited with code ${code}`);
        } else {
          const win = BrowserWindow.getFocusedWindow();
          win.loadFile("./templates/index.html");
        }
      });
    })
    .catch((err) => {
      console.log(err);
    });
}

function save_file() {}

// MENU TEMPLATE
const menuTemplate = [
  {
    label: "File",
    submenu: [
      {
        label: "Open File",
        click: () => {
          open_file();
        },
      },
      {
        label: "Save File",
        click: () => {},
      },
      // other File menu items go here
    ],
  },
  {
    // View menu
    label: "View",
    submenu: [
      { role: "reload" },
      { role: "forcereload" },
      { role: "toggledevtools" },
      { type: "separator" },
      { role: "resetzoom" },
      { role: "zoomin" },
      { role: "zoomout" },
      { type: "separator" },
      { role: "togglefullscreen" },
    ],
  },
  // other top-level menu items go here
];

// Add menu to application
const menu = Menu.buildFromTemplate(menuTemplate);
Menu.setApplicationMenu(menu);

// Create the browser window
const createWindow = () => {
  const win = new BrowserWindow({
    width: 800,
    height: 600,
    webPreferences: {
      nodeIntegration: true,
      contextIsolation: false,
    },
  });
  win.webContents.openDevTools();
  win.loadFile("./templates/index.html");
};

// Create the window when the app is ready
app.whenReady().then(() => {
  createWindow();

  app.on("activate", () => {
    if (BrowserWindow.getAllWindows().length === 0) createWindow();
  });
});
// Quit when all windows are closed, except on macOS
app.on("window-all-closed", () => {
  if (process.platform !== "darwin") app.quit();
});
