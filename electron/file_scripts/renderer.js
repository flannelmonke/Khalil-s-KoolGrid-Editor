const fs = require('fs');

function updateValue(inputField) {
  inputField.setAttribute('value', inputField.value);
  fs.writeFileSync('./templates/index.html', document.querySelector('html').outerHTML);
}
