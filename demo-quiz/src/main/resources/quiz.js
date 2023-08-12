const API_QUESTION = 'http://localhost:8080/api/question/'
let array = ["a", "b", "c", "d"] ;
let quizzes = [];
let questions;
let index = 0;


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
            questions = data.content;
            let ques = '';
            questions.forEach((question, indexQues) => {

                let randomAnswer = shuffleArray(question.answers);
                let selectAnswer = randomAnswer.slice(0,4);
                let ans = '';
                selectAnswer.forEach((answer, indexAnw) => {
                    if (question.type === "radio") {
                        ans += ` <div class="form-check">
              <input class="form-check-input" type="radio" value="${answer.content}" name="ques-${question.id}" id="answer-${answer.id}" onclick="onChoose(${indexQues})">
              <label class="form-check-label" for="answer-${answer.id}">
                  ${array[indexAnw]}. ${answer.content}
              </label>
          </div>`
                    } else if (question.type === "checkbox") {
                        ans += ` <div class="form-check">
              <input class="form-check-input" type="checkbox" value="${answer.content}" name="ques-${question.id}" id="answer-${answer.id}" onclick="onChoose(${indexQues})">
              <label class="form-check-label" for="answer-${answer.id}">
                  ${array[indexAnw]}. ${answer.content}
              </label>
          </div>`
                    } else {
                        ans += ` <div class="form-check">
              <input class="form-check-input" type="radio" value="${answer.content}" name="ques-${question.id}" id="answer-${answer.id}" onclick="onChoose(${indexQues})">
              <label class="form-check-label" for="answer-${answer.id}">
                  ${answer.content}
              </label>
          </div>`
                    }
                })
                if (question.type === "radio" || question.type === "trueOrFalse") {
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
                str2 += `<span class="btn btn-outline-dark choose-ques" id="choose-${index++}">${indexQues}</span>`
            })
            str += ` <h1 class="text-center" style="margin: 30px 0">${quizzes[0].content}</h1>
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
    for (let i = 0; i < questions.length; i++) {
        let inputName = "ques-" + questions[i].id;
        let inputElement = document.querySelectorAll('input[name="' + inputName + '"]:checked');
        if(inputElement.length <= 0){
            isFull=false;
            break;
        } else {
            let answerO = {};
            let checkAns= [];
            if (inputElement[0].type === "radio" || inputElement[0].type === "trueOrFalse") {
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
            icon: 'warning',
            title: 'Cảnh Báo',
            text: 'Bạn phải chọn hết các câu trả lời !!!'
        });
    } else {
        if (checkAnswer(values) < 5) {
            swal.fire({
                icon: 'error',
                title: 'Chia Buồn',
                text: "Bạn được " + checkAnswer(values) + "/" + questions.length + " điểm. Chúc bạn may mắn lần sau !!!"
            }).then(function () {
                window.location.href = '/home';
            });
        } else swal.fire({
            icon: 'success',
            title: 'Chúc Mừng',
            text: "Chúc mừng bạn đã được " + checkAnswer(values) + "/" + questions.length + " điểm !!!"
        }).then(function () {
            window.location.href = '/home';
        });
    }
    let data = {
        score: checkAnswer(values),
        quiz_id: quizzes[0].id
    };

    for (let i = 0; i < questions.length; i++) {
        let inputCheck = document.getElementsByName(`ques-${questions[i].id}`);
        let chooseElement = document.getElementById(`choose-${i}`);
        let isChecked = false;

        for (let j = 0; j < inputCheck.length; j++) {
            if (inputCheck[j].checked) {
                isChecked = true;
                break;
            }
        }

        if (isChecked) {
            let value = values[i];
            if (value.type === "radio") {
                let correctAnswer = questions[i].answers.find(answer => answer.status === true);
                if (correctAnswer.content === value.content) {
                    chooseElement.style.background = "#46be8a";
                    chooseElement.style.border = "none";
                } else {
                    chooseElement.style.background = "#f96868";
                    chooseElement.style.border = "none";

                }
            } else if (value.type === "checkbox") {
                let correctAnswers = questions[i].answers.filter(answer => answer.status === true);
                let selectedAnswers = value.content;
                let isCorrect = checkAnsCheckbox(correctAnswers.map(answer => answer.content), selectedAnswers);
                if (isCorrect) {
                    chooseElement.style.background = "#46be8a";
                    chooseElement.style.border = "none";
                } else {
                    chooseElement.style.background = "#f96868";
                    chooseElement.style.border = "none";
                }
            }
        }
    }

    $.ajax({
        url: '/api/save-score',
        method: 'POST',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        dataType: 'json',
        data: JSON.stringify(data),
        success: function(response) {
        },
        error: function(error) {
        }
    });

}
function checkAnswer(value){
    let score = 0;
    for (let i = 0; i < questions.length; i++) {
        if (value[i].type === "radio") {
            for (let j = 0; j < questions[i].answers.length; j++) {
                if (questions[i].answers[j].content === value[i].content && questions[i].answers[j].status === true)
                    score++;
            }
        } else if (value[i].type === "checkbox") {
            let correctAns =[]
            questions[i].answers.filter(ans => {
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

function onChoose(index){
    let inputCheck = document.getElementsByName(`ques-${questions[index].id}`);
    for (let i = 0; i < inputCheck.length; i++) {
        if(inputCheck[i].checked){
            document.getElementById(`choose-${index}`).style.background = "#0d6efd";
            document.getElementById(`choose-${index}`).style.border = "none";
            break;
        }
        else {
            document.getElementById(`choose-${index}`).style.background = "#ced4da";
            document.getElementById(`choose-${index}`).style.border = "1px solid #333";

        }
    }
}

