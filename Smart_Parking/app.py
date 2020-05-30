#!/usr/bin/python
import flask
import os
import numpy as np
import sys
import matplotlib.image as mpimg
import tensorflow as tf
from flask import jsonify
import pathlib
from keras import backend as K

app = flask.Flask(__name__)
app.config["DEBUG"] = False

K.clear_session()
path = pathlib.Path(__file__).parent.absolute()
NOMEROFF_NET_DIR = os.path.abspath(path)

MASK_RCNN_DIR = os.path.join(NOMEROFF_NET_DIR, 'Mask_RCNN')
MASK_RCNN_LOG_DIR = os.path.join(NOMEROFF_NET_DIR, 'logs')

sys.path.append(NOMEROFF_NET_DIR)

from NomeroffNet import filters, RectDetector, TextDetector, OptionsDetector, Detector, textPostprocessing, textPostprocessingAsync

global graph
graph = tf.get_default_graph()
nnet = Detector(MASK_RCNN_DIR, MASK_RCNN_LOG_DIR)
nnet.loadModel("latest")
nnet.MODEL.keras_model._make_predict_function()

rectDetector = RectDetector()

optionsDetector = OptionsDetector()
optionsDetector.load("latest")

textDetector = TextDetector.get_static_module("ru")()
textDetector.load("latest")

@app.route('/<file_name>', methods=['GET'])
def index(file_name):

    # Detect numberplate
    file_path = pathlib.Path(__file__).parent.absolute()
    file_path_str = str(file_path.resolve())
    print(file_name)
    img_path = file_path_str + '\\images\\' + file_name
    print(img_path)
    with graph.as_default():
        img = mpimg.imread(img_path)
        NP = nnet.detect([img])

        # Generate image mask.
        cv_img_masks = filters.cv_img_mask(NP)

        # Detect points.
        arrPoints = rectDetector.detect(cv_img_masks)
        zones = rectDetector.get_cv_zonesBGR(img, arrPoints)

        # find standart
        regionIds, stateIds, countLines = optionsDetector.predict(zones)
        regionNames = optionsDetector.getRegionLabels(regionIds)

        # find text with postprocessing by standart
        textArr = textDetector.predict(zones)
        textArr = textPostprocessing(textArr, regionNames)


    return jsonify({'numbers': textArr})

app.run()