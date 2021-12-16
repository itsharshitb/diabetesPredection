from flask import Flask,jsonify,request
import pickle
import numpy as np

model = pickle.load(open('model_pickle.pkl','rb'))

app = Flask(__name__)

@app.route('/')
def home():
    return "Hello world"

@app.route('/predict',methods=['POST'])
def predict():
    preg = request.form.get('preg')
    gluc = request.form.get('gluc')
    bp = request.form.get('bp')
    st = request.form.get('st')
    insulin = request.form.get('insulin')
    bmi = request.form.get('bmi')
    age = request.form.get('age')
    
    input_query = np.array([[preg,gluc,bp,st,insulin,bmi,age]])
    result = model.predict(input_query)[0]
    return jsonify({'Diabetes':str(result)})


if(__name__ == "__main__"):
    app.run(debug=True)