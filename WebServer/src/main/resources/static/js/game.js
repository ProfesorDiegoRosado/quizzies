
  // Global
  // toast notifications
  const toastMessages = new Notyf({
    types: [
      {
        type: 'rightAnswer',
        position: {
          y: 'top'
        },
        background: 'LimeGreen',
        duration: 2000,
        dismissible: true
      },
      {
        type: 'wrongAnswer',
        position: {
          y: 'top'
        },
        background: 'IndianRed',
        duration: 2000,
        dismissible: true
      },
      {
        type: 'categoryDone',
        position: {
          y: 'top'
        },
        background: 'Blue',
        duration: 2000,
        dismissible: true
      }
    ]
  });

  // Variables
  let deck;
  let colorsByCategory = {};
  let currentCategory = "";
  let round = 0;
  let score = {};


  function updateRound() {
    round++;
    $("#round-number").text(round);
  }
  // Load año
  document.getElementById("year").innerText = new Date().getFullYear();

  function loadQuestion(question) {
    // Update current category
    currentCategory = question.category.name;
    document.getElementById("current-category").innerText = currentCategory;

    // set question
    document.getElementById("question-text").innerText = question.question;
    document.getElementById("options").innerHTML = "";
    
    // Generate answer option buttons
    question.answers.forEach((answer, index) => {
      const button = document.createElement("button");
      button.classList.add("btn", "btn-secondary", "mb-2");
      button.innerText = answer;
      button.onclick = () => checkAnswer(index, question);
      document.getElementById("options").appendChild(button);
    });
  }

  function checkAnswer(selectedOption, question) {
    if (selectedOption === question.rightAnswer) {
      score[currentCategory]++;
      updateScore();
      toastAnswer(true);
    } else {
      toastAnswer(false);
    }
    nextQuestionEvent();
  }

  function toastAnswer(right) {
    // Create an instance of Notyf
    var type;
    var message;
    if (right) {
      type = 'rightAnswer';
      message = '¡Correcto!'
    } else {
      type = 'wrongAnswer';
      message = '¡Incorrecto!'
    }

    toastMessages.open({
      type: type,
      message: message
    });

  }


  function updateScore() {
    Object.keys(score).forEach(categoryName => {
      points = score[categoryName];
      catIndex = getIndexOfCategory(categoryName);
      for (let i = 0; i < 3; i++) {
        const box = document.querySelector(`#category-status-${catIndex} .score-box:nth-child(${i + 2})`);
        box.className = "score-box";
        if (i < points) {
          box.style.backgroundColor = colorsByCategory[categoryName];
        } else {
          box.classList.add("unanswered");
        }
      }
    });

  }

  function getIndexOfCategory(categoryName) {
    let categoriesNames = deck.categories.map( function(c) {
      return c['name'];
    })
    catIndex = categoriesNames.indexOf(categoryName);
    return catIndex;
  }

  function updateCategory(categories) {
    categories.forEach( (category, index) => {
      // populate score category dict
      score[category.name] = 0;
      // show categories
      $( ".category-title.cat-"+index).text(category.name);
      // set color
      $( ".category-title.cat-"+index).css('background-color',category.color.hexString);
      colorsByCategory[category.name] = category.color.hexString;
    })
  }


  // Example function to render the data
  function renderDeckName(name) {
    document.getElementById('deck-name-span').innerHTML = name;
  }

  function renderDeck(data) {
    deck = data;
    renderDeckName(data.name);
    updateCategory(data.categories);
    nextQuestionEvent();
  }

  // Function to call the REST API
  async function loadDeckFromAPI(deckName) {
    const endpoint = `http://localhost:8080/api/deck/${encodeURIComponent(deckName)}`;
    try {
      const response = await fetch(endpoint, {
        method: 'GET',
        headers: {
          'Content-Type': 'application/json',
        },
      });

      if (!response.ok) {
        throw new Error(`Error: ${response.statusText}`);
      }

      const data = await response.json();
      console.log('Deck Data:', data);

      // You can now render the data on the page
      renderDeck(data);

    } catch (error) {
      console.error('Failed to fetch data:', error);
    }
  }


  // Function to get query parameters
  function getQueryParameter(name) {
    const urlParams = new URLSearchParams(window.location.search);
    return urlParams.get(name); // Returns the value of 'deckName'
  }

  // load deck function
  function loadDeck() {
    // Get the 'deckName' parameter
    const deckName = getQueryParameter('deckName');

    // Log it to check
    console.log('Deck Name:', deckName);

    // Call the REST API if 'deckName' exists
    if (deckName) {
      loadDeckFromAPI(deckName);
    } else {
      console.error('No deckName provided in the URL');
    }

  }


  function getNextQuestion(deck, questions_categories) {
    const nextCategory = questions_categories[randomNumber(0, questions_categories.length)];
    const categoryKey = Object.keys(deck.categoryQuestionsMap).filter(
        k =>  k.includes(`name='${nextCategory}'`)
    )[0];

    const questions = deck.categoryQuestionsMap[categoryKey];

    const nextQuestionIndex = randomNumber(0, questions.length);
    return questions[nextQuestionIndex];
  }


  function loadNextQuestion() {
    let questions_categories = notDoneCategories();

    if (questions_categories.length === 0) {
      alert("¡¡¡ Juego Terminado !!!")

    } else {
      const question = getNextQuestion(deck, questions_categories);
      loadQuestion(question);
    }
  }

  function nextQuestionEvent() {
    updateRound();

    if ((currentCategory in score) && (score[currentCategory] >= 3)) {
      toastMessages.open({
        type: 'categoryDone',
        message: `Categoria <strong>${currentCategory}</strong> completada`
      });
      catIndex = getIndexOfCategory(currentCategory);
      $("#quesito-cat-" + catIndex + " > svg > path").css({ fill: colorsByCategory[currentCategory] })
      loadNextQuestion();
    } else {
      loadNextQuestion();
    }
  }

  function notDoneCategories() {
    let filtered_score = Object.entries(score).filter(([name, points]) => points < 3);
    let result = filtered_score.map(x => x[0])
    return result;
  }

  // Utils
  function randomNumber(min, max) {
    return Math.floor(Math.random() * (max - min)) + min;
  }


  // On load page
  $(function () {
    loadDeck();
  });