

function loadDecks() {
    fetch("http://localhost:8080/api/decks/info", {
        "method": "GET"
    }).then(
        response => {
            response.json().then(
                data =>{

                    console.log(data);
                    //if (data.length > 0){
                    /*
                        var temp = "";

                        data.forEach((x) => {
                            temp += "<tr>";
                            temp += "<td>"+ x.team +"</td>";
                            temp += "<td>"+ x.played +"</td>";
                            temp += "<td>"+ x.win +"</td>";
                            temp += "<td>"+ x.draw +"</td>";
                            temp += "<td>"+ x.loss +"</td>";
                            temp += "<td>"+ x.goalsFor +"</td>";
                            temp += "<td>"+ x.goalsAgainst +"</td>";
                            temp += "<td>"+ x.points +"</td>";
                            temp += "</tr>"
                        });
                     */


                        const $deckList = $('#deck-list');

                        data.forEach(deck => {
                            const categories = Object.entries(deck.categoriesNumQuestionsMap)
                                .map(([category, count]) => `${category} (${count})`)
                                .join(', ');

                            const row = `
                              <tr>
                                <td>${deck.name}</td>
                                <td>${deck.description}</td>
                                <td>${categories}</td>
                                <td>${deck.numQuestions}</td>
                              </tr>
                            `;

                            $deckList.append(row);
                        });

                        //document.getElementById("data").innerHTML = temp;
                    //}
                }
            )
        })
}

// On page load
$(function () {
    loadDecks();
});