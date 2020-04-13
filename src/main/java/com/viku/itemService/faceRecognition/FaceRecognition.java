package com.viku.itemService.faceRecognition;

import lombok.extern.slf4j.Slf4j;
import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacpp.opencv_imgcodecs;
import org.datavec.image.loader.NativeImageLoader;
import org.deeplearning4j.nn.graph.ComputationGraph;
import org.deeplearning4j.nn.graph.vertex.GraphVertex;
import org.deeplearning4j.nn.workspace.LayerWorkspaceMgr;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;
import org.nd4j.linalg.indexing.NDArrayIndex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class FaceRecognition {

    private static final double THRESHOLD = 0.57;
    private FaceNetSmallV2Model faceNetSmallV2Model;
    private ComputationGraph computationGraph;
    private static final NativeImageLoader LOADER = new NativeImageLoader(96, 96, 3);
    private INDArray transpose(INDArray indArray1) {
        INDArray one = Nd4j.create(new int[]{1, 96, 96});
        one.assign(indArray1.get(NDArrayIndex.point(0), NDArrayIndex.point(2)));
        INDArray two = Nd4j.create(new int[]{1, 96, 96});
        two.assign(indArray1.get(NDArrayIndex.point(0), NDArrayIndex.point(1)));
        INDArray three = Nd4j.create(new int[]{1, 96, 96});
        three.assign(indArray1.get(NDArrayIndex.point(0), NDArrayIndex.point(0)));
        return Nd4j.concat(0, one, two, three).reshape(new int[]{1, 3, 96, 96});
    }

    private INDArray read(String pathname) throws IOException {
        URL url = new URL(pathname);
        File temp = downloadFile(url);
        opencv_core.Mat imread = opencv_imgcodecs.imread(temp.getAbsolutePath(), 1);
        temp.delete();
        INDArray indArray = LOADER.asMatrix(imread);
        return transpose(indArray);
    }
    private File downloadFile(URL url){
        try (InputStream in = url.openStream()){
            File tempfile = new File("tempfile.jpg");
            Files.copy(in, Paths.get(tempfile.getAbsolutePath()));
            return tempfile;
        }
        catch (Exception e){
          return null;
        }

    }
    private INDArray forwardPass(INDArray indArray) {
        Map<String, INDArray> output = computationGraph.feedForward(indArray, false);
        GraphVertex embeddings = computationGraph.getVertex("encodings");
        INDArray dense = output.get("dense");
        embeddings.setInputs(dense);
        INDArray embeddingValues = embeddings.doForward(false, LayerWorkspaceMgr.builder().defaultNoWorkspace().build());
        log.debug("dense = " + dense);
        log.debug("encodingsValues = " + embeddingValues);
        return embeddingValues;
    }

    private double distance(INDArray a, INDArray b) {
        return a.distance2(b);
    }

    public void loadModel() throws Exception {
        faceNetSmallV2Model = new FaceNetSmallV2Model();
        computationGraph = faceNetSmallV2Model.init();
        log.info(computationGraph.summary());
    }

    public void registerNewMember(String memberId, String imagePath,HashMap<String, INDArray> memberEncodingsMap) throws IOException {
        INDArray read = read(imagePath);
        memberEncodingsMap.put(memberId, forwardPass(normalize(read)));
    }

    private static INDArray normalize(INDArray read) {
        return read.div(255.0);
    }

    public String whoIs(String imagePath,HashMap<String, INDArray> memberEncodingsMap) throws IOException {
        INDArray read = read(imagePath);
        INDArray encodings = forwardPass(normalize(read));
        double minDistance = Double.MAX_VALUE;
        String foundUser = "";

        for (Map.Entry<String, INDArray> entry : memberEncodingsMap.entrySet()) {
            INDArray value = entry.getValue();
            double distance = distance(value, encodings);
            log.info("distance of " + entry.getKey() + " with " + new File(imagePath).getName() + " is " + distance);
            if (distance < minDistance) {
                minDistance = distance;
                foundUser = entry.getKey();
            }
        }
        if (minDistance > THRESHOLD) {
            foundUser = "Unknown user";
        }
        log.info(foundUser + " with distance " + minDistance);
        return foundUser;
    }
}
