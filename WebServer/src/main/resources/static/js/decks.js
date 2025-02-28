

function loadDecks() {
    fetch(document.location.origin + "/api/decks/info", {
        "method": "GET"
    }).then(
        response => {
            response.json().then(
                data => {
                    console.log(data);
                    const $deckList = $('#deck-list');

                    data.forEach(deck => {
                        const categories = Object.entries(deck.categoriesNumQuestionsMap)
                            .map(([category, count]) => `${category} (${count})`)
                            .join(', ');

                        const row = `
                          <tr onclick="onClickTableRow(this)">
                            <td>${deck.name}</td>
                            <td>${deck.description}</td>
                            <td>${categories}</td>
                            <td>${deck.numQuestions}</td>
                          </tr>
                        `;

                        $deckList.append(row);
                    });

                }
            )
        }
    )
}

function onClickTableRow(row) {
    //alert("Row index is " + row.rowIndex);

    const deckName = row.childNodes[1].textContent;
    const urlEncodedDeckName = encodeURIComponent(deckName)

    location.href = document.location.origin + `/game.html?deckName=${urlEncodedDeckName}`
}

// On page load
$(function () {
    loadDecks();
});