const API_QUESTION = 'http://localhost:8080/api/question/'
let array = ["a", "b", "c", "d"] ;
let quizzes = [];
let selectQuestion;


function loadQuiz(quizId) {
    $.ajax({
        url: `http://localhost:8080/api/quiz/${quizId}`,
        method: 'GET',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        success: function(data) {
            renderQuiz(data);
        },
        error: function(error) {
            console.error(error);
        }
    });
}
function initData() {
    return new Promise((resolve, reject) => {
        $.ajax({
            url: 'http://localhost:8080/api/quiz',
            method: 'GET',
        }).done(data => {
            resolve(data);
        }).fail(error => {
            reject(error);
        });
    });
}


function renderQuiz(){
    initData().then(data => {
        quizzes = data.content;
        $.ajax({
            url: API_QUESTION + quizzes[0].id,
            method: "GET",
            headers:{
                'Accept':'application/json',
                'Content-Type': 'application/json'},
        }).done(data => {
            console.log(data.content)
            let str = '';
            let str2 = '';
            let randomQuestion = shuffleArray(data.content);
            selectQuestion = randomQuestion.slice(0,9);
            let ques = '';
            selectQuestion.forEach((question, indexQues) => {

                let randomAnswer = shuffleArray(question.answers);
                let selectAnswer = randomAnswer.slice(0,4);
                let ans = '';
                selectAnswer.forEach((answer, indexAnw) => {
                    if (question.type === "radio") {
                        ans += ` <div class="form-check">
              <input class="form-check-input" type="radio" value="${answer.content}" name="ques-${question.id}" id="answer-${answer.id}">
              <label class="form-check-label" for="answer-${answer.id}">
                  ${array[indexAnw]}. ${answer.content}
              </label>
          </div>`
                    } else if (question.type === "checkbox") {
                        ans += ` <div class="form-check">
              <input class="form-check-input" type="checkbox" value="${answer.content}" name="ques-${question.id}" id="answer-${answer.id}">
              <label class="form-check-label" for="answer-${answer.id}">
                  ${array[indexAnw]}. ${answer.content}
              </label>
          </div>`
                    } else {
                        ans += ` <div class="form-check">
              <input class="form-check-input" type="radio" value="${answer.content}" name="ques-${question.id}" id="answer-${answer.id}">
              <label class="form-check-label" for="answer-${answer.id}">
                  ${answer.content}
              </label>
          </div>`
                    }
                })
                if (question.type === "radio" || question.type === "true/false") {
                    ques += ` <h3 class="alert alert-secondary" id="ques-${question.id}">${++indexQues}. ${question.content}</h3>
                        <span class="h5">Select one:</span>
            ${ans}
          `
                } else {
                    ques += ` <h3 class="alert alert-secondary" id="ques-${question.id}">${++indexQues}. ${question.content}</h3>
                        <span class="h5">Select one or more:</span>
            ${ans}
          `
                }
                str2 += `<span class="btn btn-outline-dark" id="ques-${question.id}" style="margin-right: 5%">${indexQues}</span>`
            })
            str += ` <h1 class="text-center">${quizzes[0].content}</h1>
  <div id="">
      <div id="question">
         ${ques}
      </div>
  </div>`
            document.getElementById("quiz").innerHTML = str;
            document.getElementById("select-question").innerHTML = str2;
        })
    })

}
renderQuiz();



function shuffleArray(array) {
    for (let i = array.length - 1; i > 0; i--) {
        const j = Math.floor(Math.random() * (i + 1));
        [array[i], array[j]] = [array[j], array[i]];
    }
    return array;
}

function submit(){
    let values = [];
    let isFull = true;
    for (let i = 0; i < selectQuestion.length; i++) {
        let inputName = "ques-" + selectQuestion[i].id;
        let inputElement = document.querySelectorAll('input[name="' + inputName + '"]:checked');
        if(inputElement.length <= 0){
            isFull=false;
            break;
        } else {
            let answerO = {};
            let checkAns= [];
            if (inputElement[0].type === "radio" || inputElement[0].type === "true/false") {
                valueInput = inputElement[0].value;
                answerO.content = valueInput;
                answerO.type = "radio"
                values.push(answerO);
            } else  {
                inputElement.forEach(function(checkbox) {
                    checkAns.push(checkbox.value);
                });
                answerO.content = checkAns;
                answerO.type = "checkbox"
                values.push(answerO);
            }
        }
    }
    if(!isFull){
        swal.fire({
            icon: 'error',
            title: 'Error',
            text: 'Chọn hết các câu trả lời đi !!!'
        });
    } else {
        swal.fire({
            icon: 'success',
            title: 'Success',
            text: "Chúc mừng bạn đã được " + checkAnswer(values) + "/" + selectQuestion.length + " điểm"
        });
    }
    console.log(values)
}
function checkAnswer(value){
    let score = 0;
    for (let i = 0; i < selectQuestion.length; i++) {
        if (value[i].type === "radio") {
            for (let j = 0; j < selectQuestion[i].answers.length; j++) {
                if (selectQuestion[i].answers[j].content === value[i].content && selectQuestion[i].answers[j].status === true)
                    score++;
            }
        } else if (value[i].type === "checkbox") {
            let correctAns =[]
            selectQuestion[i].answers.filter(ans => {
                if(ans.status === true){
                    correctAns.push(ans.content)
                }
            });
            if(checkAnsCheckbox(correctAns,value[i].content)){
                score++;
            }
        }
    }
    return score;
}
function checkAnsCheckbox(array1, array2) {
    if (array1.length !== array2.length) {
        return false;
    }
    let ans = true;
    for (let i = 0; i < array1.length; i++) {
        if (array2.indexOf(array1[i]) === -1) {
            ans = false;
            break;
        }
    }
    return ans;
}

