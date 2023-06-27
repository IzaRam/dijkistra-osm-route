package com.example.demo;

import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MapService {

    private final NepRepository nepRepository;
    private final VertexRepository vertexRepository;

    public MapService(NepRepository nepRepository, VertexRepository vertexRepository) {
        this.nepRepository = nepRepository;
        this.vertexRepository = vertexRepository;
    }

    private static Node getLowestDistanceNode(Set < Node > unsettledNodes) {
        Node lowestDistanceNode = null;
        double lowestDistance = Double.MAX_VALUE;
        for (Node node: unsettledNodes) {
            double nodeDistance = node.getDistance();
            if (nodeDistance < lowestDistance) {
                lowestDistance = nodeDistance;
                lowestDistanceNode = node;
            }
        }
        return lowestDistanceNode;
    }

    private static void calculateMinimumDistance(Node evaluationNode,
                                                 Double edgeWeigh, Node sourceNode) {
        Double sourceDistance = sourceNode.getDistance();
        if (sourceDistance + edgeWeigh < evaluationNode.getDistance()) {
            evaluationNode.setDistance(sourceDistance + edgeWeigh);
            LinkedList<Node> shortestPath = new LinkedList<>(sourceNode.getShortestPath());
            shortestPath.add(sourceNode);
            evaluationNode.setShortestPath(shortestPath);
        }
    }

    public List<Nep2po4pgr> findPathLatLon(Integer sourceId, Integer targetId) {
        Node node = getShortestPathToDestinationNode(sourceId, targetId);
        List<Nep2po4pgr> edges = new ArrayList<>();

        for (int i=1; i < node.getShortestPath().size(); i++) {
            Integer source = node.getShortestPath().get(i-1).getId();
            Integer target = node.getShortestPath().get(i).getId();
            nepRepository.findBySourceAndTarget(source, target).ifPresent(edges::add);
        }

        nepRepository.findBySourceAndTarget(node.getShortestPath().get(node.getShortestPath().size()-1).getId(), targetId)
                .ifPresent(edges::add);

        return edges;
    }

    public Node getShortestPathToDestinationNode(Integer sourceId, Integer targetId) {
        Graph graph = getShortestPathsFromSource(sourceId);

        for (Node n : graph.getNodes()) {
            if (Objects.equals(n.getId(), targetId)) {
                return n;
            }
        }
        return null;
    }

    public Graph getShortestPathsFromSource(Integer sourceId) {
        Graph graph = createGraph();
        Set<Node> nodes = graph.getNodes();
        Node source = new Node(0);
        for (Node n : nodes) {
            if (Objects.equals(n.getId(), sourceId))
                source = n;
        }

        source.setDistance(0.0);

        Set<Node> settledNodes = new HashSet<>();
        Set<Node> unsettledNodes = new HashSet<>();

        unsettledNodes.add(source);

        while (unsettledNodes.size() != 0) {
            Node currentNode = getLowestDistanceNode(unsettledNodes);
            unsettledNodes.remove(currentNode);
            for (Map.Entry< Node, Double> adjacencyPair: currentNode.getAdjacentNodes().entrySet()) {
                Node adjacentNode = adjacencyPair.getKey();
                Double edgeWeight = adjacencyPair.getValue();
                if (!settledNodes.contains(adjacentNode)) {
                    calculateMinimumDistance(adjacentNode, edgeWeight, currentNode);
                    unsettledNodes.add(adjacentNode);
                }
            }
            settledNodes.add(currentNode);
        }
        return graph;
    }

    public Graph createGraph() {
        Graph graph = new Graph();

        List<Nep2poVertex> vertexList = vertexRepository.findAll();

        Map<Integer, Node> nodeMap = new HashMap<>();

        vertexList.forEach(v -> {
            if (v.id == 4)
                System.out.println();
            Node node = nodeMap.get(v.id);
            if (node == null) node = new Node(v.id);
            List<Nep2po4pgr> destinationNepList = nepRepository.findBySource(v.id);
            for (Nep2po4pgr n : destinationNepList){
                Node destinationNode = nodeMap.get(n.target);
                if (destinationNode == null) destinationNode = new Node(n.target);
                node.addDestination(destinationNode, n.km);
                nodeMap.putIfAbsent(n.target, destinationNode);
            }
            nodeMap.put(v.id, node);
        });

        for (Map.Entry<Integer, Node> pair : nodeMap.entrySet()) {
            if (pair.getKey() == 618)
                System.out.println();
            graph.addNode(pair.getValue());
        }

        return graph;
    }
}
