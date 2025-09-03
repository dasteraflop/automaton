// Create a context menu item when the extension is installed
chrome.runtime.onInstalled.addListener(() => {
  chrome.contextMenus.create({
    id: "sendSelection",
    title: "Generate resume",
    contexts: ["selection"]
  });
});

// Listen for clicks on the context menu item
chrome.contextMenus.onClicked.addListener((info, tab) => {
  if (info.menuItemId === "sendSelection") {
    // When the item is clicked, send a message to the content script
    // to get the selected text and perform the request.
    chrome.tabs.sendMessage(tab.id, {
      type: "sendSelectionRequest"
    });
  }
});

// Listen for messages from the content script
chrome.runtime.onMessage.addListener((request, sender, sendResponse) => {
  if (request.type === "selectionResult") {
    const selectedText = request.data;
    console.log("Received selected text:", selectedText);
    
    // Here, send the selected text to your desired API endpoint.
    // Replace with your actual request logic.
    fetch("http://localhost:9000/generate", {
      method: "POST",
      headers: {
        "Content-Type": "application/json"
      },
      body: JSON.stringify({ text: selectedText })
    })
    .then(response => response.json())
    .then(data => console.log("Request successful:", data))
    .catch(error => console.error("Request failed:", error));
  }
});

