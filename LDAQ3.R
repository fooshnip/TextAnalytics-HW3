# 11/11/2013
# Adrianna Jelesnianska
# Text Analytics Assignment 3- LDA

#What is LDA?
#http://sumidiot.wordpress.com/2012/06/13/lda-from-scratch/

# Load files
TF = read.delim("~/workspace/TextAnalytics-HW3/termdoc.txt", header=F)
TF = as.data.frame(apply(TF,2,function(x) iconv(enc2utf8(x), sub = "byte")))

library(sqldf); 
TF2 <- sqldf('select V1, sum(V2) as V2,  sum(V3) as V3,	
             sum(V4) as V4,	sum(V5) as V5,	sum(V6) as V6,	sum(V7) as V7,	sum(V8) as V8,	
             sum(V9) as V9,	sum(V10) as V10,	sum(V11) as V11,	sum(V12) as V12,	sum(V13) as V13,	
             sum(V14) as V14,	sum(V15) as V15,	sum(V16) as V16,	sum(V17) as V17,	sum(V18) as V18,	
             sum(V19) as V19,	sum(V20) as V20,	sum(V21) as V21,	sum(V22) as V22,	sum(V23) as V23,
             sum(V24) as V24,
             sum(V25) as V25,	sum(V26) as V26,	sum(V27) as V27,	sum(V28) as V28,	sum(V29) as V29,	
             sum(V30) as V30,	sum(V31) as V31,	sum(V32) as V32,	sum(V33) as V33,	sum(V34) as V34,
             sum(V35) as V35,	sum(V36) as V36,	sum(V37) as V37,	sum(V38) as V38,	sum(V39) as V39,	
             sum(V40) as V40,	sum(V41) as V41,	sum(V42) as V42,	sum(V43) as V43,	sum(V44) as V44,	
             sum(V45) as V45,	sum(V46) as V46,	sum(V47) as V47,	sum(V48) as V48,	sum(V49) as V49,	
             sum(V50) as V50,	sum(V51) as V51,	sum(V52) as V52,	sum(V53) as V53,	sum(V54) as V54,
             sum(V55) as V55,	sum(V56) as V56,	sum(V57) as V57,	sum(V58) as V58,	sum(V59) as V59,	
             sum(V60) as V60,	sum(V61) as V61,	sum(V62) as V62,	sum(V63) as V63,	sum(V64) as V64,	
             sum(V65) as V65,	sum(V66) as V66,	sum(V67) as V67,	sum(V68) as V68,	sum(V69) as V69,
             sum(V70) as V70,	sum(V71) as V71,	sum(V72) as V72,	sum(V73) as V73,	sum(V74) as V74,
             sum(V75) as V75,	sum(V76) as V76,	sum(V77) as V77,	sum(V78) as V78,	sum(V79) as V79,
             sum(V80) as V80,	sum(V81) as V81,	sum(V82) as V82,	sum(V83) as V83,	sum(V84) as V84,
             sum(V85) as V85,	sum(V86) as V86,	sum(V87) as V87,	sum(V88) as V88,	sum(V89) as V89,
             sum(V90) as V90,	sum(V91) as V91,	sum(V92) as V92,	sum(V93) as V93,	sum(V94) as V94,
             sum(V95) as V95,	sum(V96) as V96,	sum(V97) as V97,	sum(V98) as V98,	sum(V99) as V99,
             sum(V100) as V100,	sum(V101) as V101,	sum(V102) as V102,	sum(V103) as V103,	sum(V104) as V104,
             sum(V105) as V105,	sum(V106) as V106,	sum(V107) as V107,	sum(V108) as V108,	sum(V109) as V109,
             sum(V110) as V110,	sum(V111) as V111,	sum(V112) as V112,	sum(V113) as V113,	sum(V114) as V114,
             sum(V115) as V115,	sum(V116) as V116,	sum(V117) as V117,	sum(V118) as V118,	sum(V119) as V119,
             sum(V120) as V120,	sum(V121) as V121,	sum(V122) as V122,	sum(V123) as V123,	sum(V124) as V124,
             sum(V125) as V125,	sum(V126) as V126,	sum(V127) as V127,	sum(V128) as V128,	sum(V129) as V129,
             sum(V130) as V130,	sum(V131) as V131,	sum(V132) as V132,	sum(V133) as V133,	sum(V134) as V134,	
             sum(V135) as V135,	sum(V136) as V136,	sum(V137) as V137,	sum(V138) as V138,	sum(V139) as V139,
             sum(V140) as V140,	sum(V141) as V141,	sum(V142) as V142,	sum(V143) as V143,	sum(V144) as V144,
             sum(V145) as V145,	sum(V146) as V146,	sum(V147) as V147,	sum(V148) as V148,	sum(V149) as V149,
             sum(V150) as V150,	sum(V151) as V151,	sum(V152) as V152,	sum(V153) as V153,	sum(V154) as V154,
             sum(V155) as V155,	sum(V156) as V156,	sum(V157) as V157,	sum(V158) as V158,	sum(V159) as V159,
             sum(V160) as V160,	sum(V161) as V161,	sum(V162) as V162,	sum(V163) as V163,	sum(V164) as V164,
             sum(V165) as V165,	sum(V166) as V166,	sum(V167) as V167,	sum(V168) as V168,	sum(V169) as V169,
             sum(V170) as V170,	sum(V171) as V171,	sum(V172) as V172,	sum(V173) as V173,	sum(V174) as V174,
             sum(V175) as V175,	sum(V176) as V176,	sum(V177) as V177,	sum(V178) as V178,	sum(V179) as V179,
             sum(V180) as V180,	sum(V181) as V181,	sum(V182) as V182,	sum(V183) as V183,	sum(V184) as V184,
             sum(V185) as V185,	sum(V186) as V186,	sum(V187) as V187,	sum(V188) as V188,	sum(V189) as V189,
             sum(V190) as V190,	sum(V191) as V191,	sum(V192) as V192,	sum(V193) as V193,	sum(V194) as V194,
             sum(V195) as V195,	sum(V196) as V196,	sum(V197) as V197,	sum(V198) as V198,	sum(V199) as V199,
             sum(V200) as V200,	sum(V201) as V201,	sum(V202) as V202,	sum(V203) as V203 from TF
             group by V1') #some values were duplicated - need to group
TF2 <- TF2[-grep("[^[:alpha:]]",TF2$V1),]
TF2 <- TF2[nchar(TF2$V1)>4,]
TF2 <- TF2[(TF2$V1=="country")==FALSE,]
TF2 <- TF2[(TF2$V1=="government")==FALSE,]
TF2 <- TF2[(TF2$V1=="population")==FALSE,]
TF2 <- TF2[(TF2$V1=="international")==FALSE,]

rownames(TF2) = TF2[,1] #setting the terms as row names
TF2 <- TF2[,-1] #removing the term to calculate sums over all documents
TermTotalFreq = rowSums(TF2) #creating the term total frequency vector

MainTerms = names(TermTotalFreq[TermTotalFreq > 200]) #1186 terms
TF3=TF2[MainTerms,] 
head(TF3)

colTotals <- apply(TF3, 2, sum) #Make sure each document is non empty
TF3  <- TF3[,colTotals > 0] 


library(tm);library(slam) # tm for TermDocumentMatrix and slam for simple_triplet_matrix
library(topicmodels) #library for LDA

# Input for LDA() should be either TermDocumentMatrix or simple_triplet_matrix.
s <- as.simple_triplet_matrix(t(TF3)) # data.frame -> simple_triplet_matrix
NumTopics <- 10
# default control values
control_LDA_VEM <- list(estimate.alpha = TRUE, alpha= 50/10, estimate.beta = TRUE,
                        verbose = 0, prefix = tempfile(), save = 0, keep = 0,
                        seed = as.integer(Sys.time()), nstart = 1, best = TRUE,
                        var = list(iter.max = 500, tol = 10^-6),
                        em = list(iter.max = 1000, tol = 10^-4),
                        initialize = "random")
result.LDA <- LDA(s, NumTopics, method="VEM", control=control_LDA_VEM) 


# Analysis of the fitted result
topic.dist <- posterior(result.LDA)
TopTopics <- topics(result.LDA, 1) # Most Likely Topic for each document. (not relavant to the HW)
TopTerms <- terms(result.LDA, 10) #10 most frequent term for 10 topics